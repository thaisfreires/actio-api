package com.actio.actio_api.db;

import com.actio.actio_api.model.Movement;
import com.actio.actio_api.model.StockTransaction;
import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.service.ActioUserService;
import com.actio.actio_api.service.MovementService;
import com.actio.actio_api.service.StockTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Component responsible for initializing the database by executing structured SQL scripts.
 * This includes table creation, trigger setup, and loading initial data.
 * Scripts are only executed if the target table does not exist, preventing duplication.
 *
 * The scripts must be stored under the 'scripts/' directory in the classpath.
 * Supported SQL Server-specific statements (e.g., 'GO') are handled via configured separator.
 */
@Component
@RequiredArgsConstructor
public class ScriptExecutor {

    private final DataSource dataSource;
    private final ActioUserService actioUserService;
    private final DataProvider userLoader;
    public final MovementService   movementService;
    private final StockTransactionService stockTransactionService;

    /**
     * Event listener triggered after the Spring Boot application starts.
     *
     * This method checks whether the key table 'actio_user' exists in the database.
     * If the table is not present, it executes initialization SQL scripts to create the schema,
     * add triggers, insert enum values, and seed default data.
     *
     * After executing the scripts, it also loads users, movements, and stock transactions
     * into the system using predefined loaders.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadDataBase(){
        if(mustLoad("actio_user")) {
            ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
            pop.setSeparator("GO");
            pop.addScript(new ClassPathResource("scripts/schema.sql"));
            pop.addScript(new ClassPathResource("scripts/triggers.sql"));
            pop.addScript(new ClassPathResource("scripts/data_enums.sql"));
            pop.addScript(new ClassPathResource("scripts/data.sql"));
            pop.execute(dataSource);
            System.out.println("#### DataSource executed");
            this.loadUsers();
            this.loadMovements();
            this.loadTransactions();
        }
    }

    /**
     * Loads and saves default users into the system.
     *
     * This method retrieves a predefined list of users from the userLoader,
     * then saves each user using the ActioUserService.
     */
    private void loadUsers(){
        List<UserRegistrationRequest> users = userLoader.getUsersList();
        System.out.println("### Loading users");
        for (UserRegistrationRequest user : users ) {
            actioUserService.save(user);
        }
        System.out.println("### Users loaded");
    }

    /**
     * Loads and saves predefined movement records into the system.
     *
     * This method retrieves a list of movements from the userLoader and
     * persists them using the MovementService.
     */
    public  void loadMovements(){
        List<Movement> movements = userLoader.getMovementList();
        System.out.println("### Loading movements");
        for (Movement mov : movements ) {
            movementService.save(mov);
        }
        System.out.println("### Movements loaded");

    }

    /**
     * Loads and saves predefined stock transactions into the system.
     *
     * This method retrieves a list of stock transactions from the userLoader
     * and saves them using the StockTransactionService.
     */
    private void loadTransactions(){
        List<StockTransaction> transactions = userLoader.getTransactionsList();
        System.out.println("### Loading transations");
        for (StockTransaction t : transactions ) {
            stockTransactionService.saveTransaction(t);
        }
        System.out.println("### Transactions loaded");
    }

    /**
     * Verifies whether the specified table exists in the current database.
     *
     * @param table the name of the table to check
     * @return true if the table does not exist (meaning scripts should be executed), false otherwise
     */
    public boolean mustLoad(String table) {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, table, new String[]{"TABLE"})) {
                return !tables.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

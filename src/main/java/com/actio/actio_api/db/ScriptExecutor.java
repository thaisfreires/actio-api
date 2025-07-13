package com.actio.actio_api.db;

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

    /**
     * Event listener triggered after Spring Boot completes startup.
     * Checks whether the key table 'actio_user' exists, and if not,
     * executes all setup scripts to build and populate the schema.
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
        }
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

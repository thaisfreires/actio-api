package com.actio.actio_api.db;

import com.actio.actio_api.model.Movement;
import com.actio.actio_api.model.StockTransaction;
import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.service.AccountService;
import com.actio.actio_api.service.MovementTypeService;
import com.actio.actio_api.service.StockService;
import com.actio.actio_api.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DataProvider {

    private final AccountService accountService;
    private final MovementTypeService movementTypeService;
    private final StockService stockService;
    private final TransactionTypeService transactionTypeService;


    public List<StockTransaction> getTransactionsList(){
        List<StockTransaction> stockTransactions = new ArrayList<>();

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(1L))
                .negotiationPrice(new BigDecimal("3.40"))
                .quantity(80)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(26))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(2L))
                .negotiationPrice(new BigDecimal("10.50"))
                .quantity(25)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(23))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(3L))
                .negotiationPrice(new BigDecimal("4.90"))
                .quantity(30)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(19))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(1L))
                .negotiationPrice(new BigDecimal("3.45"))
                .quantity(100)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(23))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(2L))
                .negotiationPrice(new BigDecimal("10.25"))
                .quantity(30)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(21))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(3L))
                .negotiationPrice(new BigDecimal("4.70"))
                .quantity(40)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(18))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(1L))
                .negotiationPrice(new BigDecimal("3.60"))
                .quantity(110)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(27))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(2L))
                .negotiationPrice(new BigDecimal("10.10"))
                .quantity(50)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(24))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(3L))
                .negotiationPrice(new BigDecimal("4.80"))
                .quantity(60)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(22))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(4L))
                .negotiationPrice(new BigDecimal("0.16"))
                .quantity(600)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(21))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(4L))
                .negotiationPrice(new BigDecimal("0.15"))
                .quantity(300)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(19))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(4L))
                .negotiationPrice(new BigDecimal("0.17"))
                .quantity(700)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(18))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(2L))
                .negotiationPrice(new BigDecimal("10.20"))
                .quantity(40)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(17))
                .build());
        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(1L))   // EDP.LS ~ €3.5
                .negotiationPrice(new BigDecimal("3.55"))
                .quantity(100)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(14))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(2L))   // PETR4.SA ~ BRL 39
                .negotiationPrice(new BigDecimal("38.90"))
                .quantity(15)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(13))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(3L))   // AIR.PA ~ €150
                .negotiationPrice(new BigDecimal("149.80"))
                .quantity(5)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(12))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(4L))   // OR.PA ~ €430
                .negotiationPrice(new BigDecimal("428.60"))
                .quantity(2)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(11))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(5L))   // GALP.LS ~ €10
                .negotiationPrice(new BigDecimal("10.05"))
                .quantity(80)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(10))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(6L))   // NOS.LS ~ €4.5
                .negotiationPrice(new BigDecimal("4.45"))
                .quantity(50)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(9))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(7L))   // BCP.LS ~ €0.15
                .negotiationPrice(new BigDecimal("0.14"))
                .quantity(500)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(8))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(26L))  // MC.PA (LVMH) ~ €765
                .negotiationPrice(new BigDecimal("762.00"))
                .quantity(1)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(7))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(39L))  // VALE3.SA ~ BRL 65
                .negotiationPrice(new BigDecimal("65.10"))
                .quantity(10)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(6))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(41L))  // BBDC4.SA ~ BRL 16
                .negotiationPrice(new BigDecimal("16.25"))
                .quantity(20)
                .transactionType(transactionTypeService.findById(1))
                .transactionDateTime(LocalDateTime.now().minusDays(5))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(1L)) // EDP.LS
                .negotiationPrice(new BigDecimal("3.65"))
                .quantity(50)
                .transactionType(transactionTypeService.findById(2)) // SELL
                .transactionDateTime(LocalDateTime.now().minusDays(13))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(2L)) // PETR4.SA
                .negotiationPrice(new BigDecimal("39.10"))
                .quantity(10)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(12))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(7L)) // BCP.LS
                .negotiationPrice(new BigDecimal("0.15"))
                .quantity(200)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(11))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(3L)) // AIR.PA
                .negotiationPrice(new BigDecimal("150.10"))
                .quantity(2)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(10))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(1L))
                .stock(stockService.findByStockId(4L)) // OR.PA
                .negotiationPrice(new BigDecimal("430.00"))
                .quantity(1)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(9))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(3L)) // AIR.PA
                .negotiationPrice(new BigDecimal("151.20"))
                .quantity(2)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(8))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(4L)) // OR.PA
                .negotiationPrice(new BigDecimal("432.00"))
                .quantity(1)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(7))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(26L)) // MC.PA (LVMH)
                .negotiationPrice(new BigDecimal("765.00"))
                .quantity(1)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(6))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(41L)) // BBDC4.SA
                .negotiationPrice(new BigDecimal("16.40"))
                .quantity(10)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(5))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(2L))
                .stock(stockService.findByStockId(1L)) // EDP.LS
                .negotiationPrice(new BigDecimal("3.70"))
                .quantity(60)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(4))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(5L)) // GALP.LS
                .negotiationPrice(new BigDecimal("10.20"))
                .quantity(30)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(8))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(6L)) // NOS.LS
                .negotiationPrice(new BigDecimal("4.55"))
                .quantity(20)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(7))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(39L)) // VALE3.SA
                .negotiationPrice(new BigDecimal("65.20"))
                .quantity(8)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(6))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(1L)) // EDP.LS
                .negotiationPrice(new BigDecimal("3.80"))
                .quantity(50)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(5))
                .build());

        stockTransactions.add(StockTransaction.builder()
                .account(accountService.getById(3L))
                .stock(stockService.findByStockId(2L)) // PETR4.SA
                .negotiationPrice(new BigDecimal("39.40"))
                .quantity(10)
                .transactionType(transactionTypeService.findById(2))
                .transactionDateTime(LocalDateTime.now().minusDays(4))
                .build());

        Random rand = new Random();

        for (int i = 0; i < 50; i++) {
            long accountId = rand.nextInt(100) + 1;  // contas de 1 a 100
            int stockId = (i < 20) ? rand.nextInt(4) + 1 : rand.nextInt(49) + 1; // prioriza ações 1–4

            BigDecimal avgPrice = switch (stockId) {
                case 1  -> new BigDecimal("3.50");   // EDP.LS
                case 2  -> new BigDecimal("39.00");  // PETR4.SA
                case 3  -> new BigDecimal("150.00"); // AIR.PA
                case 4  -> new BigDecimal("430.00"); // OR.PA
                default -> BigDecimal.valueOf(rand.nextInt(96) + 5); // R$5–100 aleatório
            };

            int maxQty = BigDecimal.valueOf(500).divide(avgPrice, 0, RoundingMode.DOWN).intValue();
            int qty = rand.nextInt(Math.max(1, maxQty)) + 1;

            stockTransactions.add(StockTransaction.builder()
                    .account(accountService.getById(accountId))
                    .stock(stockService.findByStockId((long) stockId))
                    .negotiationPrice(avgPrice)
                    .quantity(qty)
                    .transactionType(transactionTypeService.findById(1)) // COMPRA
                    .transactionDateTime(LocalDateTime.now().minusDays(rand.nextInt(30) + 1))
                    .build());
        }

        return stockTransactions;

    }

    public List<Movement> getMovementList(){
        List<Movement> movements = new ArrayList<>();

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("500.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(29))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("350.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(27))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("400.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(25))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("800.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(28))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("500.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(24))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("300.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(22))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("900.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(30))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("450.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(28))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("650.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(25))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("300.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(20))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("250.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(20))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("200.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(19))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("100.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(18))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("150.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(17))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("350.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(16))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("600.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(15))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("1200.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(13))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("900.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(14))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("1300.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(12))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("1000.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(10))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("700.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(9))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("400.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(8))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("300.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(7))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("600.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(6))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("550.00"))
                .movementType(movementTypeService.getById(1))
                .movementDateTime(LocalDateTime.now().minusDays(5))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(1L))
                .amount(new BigDecimal("200.00"))
                .movementType(movementTypeService.getById(2)) // RESGATE
                .movementDateTime(LocalDateTime.now().minusDays(4))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(2L))
                .amount(new BigDecimal("300.00"))
                .movementType(movementTypeService.getById(2))
                .movementDateTime(LocalDateTime.now().minusDays(3))
                .build());

        movements.add(Movement.builder()
                .account(accountService.getById(3L))
                .amount(new BigDecimal("250.00"))
                .movementType(movementTypeService.getById(2))
                .movementDateTime(LocalDateTime.now().minusDays(2))
                .build());

        for (long accId = 4L; accId <= 100L; accId++) {
            BigDecimal amount = BigDecimal.valueOf(new Random().nextInt(79901) + 100); // 100–80000

            movements.add(Movement.builder()
                    .account(accountService.getById(accId))
                    .amount(amount)
                    .movementType(movementTypeService.getById(1))
                    .movementDateTime(LocalDateTime.now().minusDays(new Random().nextInt(30) + 1))
                    .build());
        }

        return movements;
    }
    public List<UserRegistrationRequest> getUsersList() {
        List<UserRegistrationRequest> users = new ArrayList<>();

        // CLIENTS
        users.add(new UserRegistrationRequest("Alice Pereira",
                "123456789",
                LocalDate.of(1990, 5, 10),
                "alice@teste.com",
                "senhadaalice"
        ));
        users.add(new UserRegistrationRequest(
                "Bruno Costa",
                "987654321",
                LocalDate.of(1985, 11, 22),
                "bruno@teste.com",
                "senhadobruno"
        ));
        users.add(new UserRegistrationRequest(
                "Carla Silva",
                "456789123",
                LocalDate.of(1992, 2, 15),
                "carla@teste.com",
                "senhadacarla"
        ));
        // ADMIN
        users.add(new UserRegistrationRequest(
                "Thais Freire",
                "100000148",
                LocalDate.of(1978, 7, 9),
                "thaisfreire@actio.com",
                "senhadoadmin"));
        users.add(new UserRegistrationRequest(
                "Isabel Veloso",
                "100000149",
                LocalDate.of(1985, 2, 20),
                "isabelveloso@actio.com",
                "senhadoadmin"));
        users.add(new UserRegistrationRequest(
                "Pedro Tavares",
                "100000150",
                LocalDate.of(1992, 11, 30),
                "pedrotavares@actio.com",
                "senhadoadmin"));
        users.add(new UserRegistrationRequest(
                "Priscila Campos",
                "100000151",
                LocalDate.of(1973, 3, 15),
                "priscilacampos@actio.com",
                "senhadoadmin"));
        // MORE CLIENTS
        users.add(new UserRegistrationRequest("Teresa Carvalho",  "100000098", LocalDate.of(1970, 9, 12),  "cliente098@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Vânia Brito",       "100000099", LocalDate.of(1985, 11, 4),  "cliente099@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Natália Maciel",    "100000100", LocalDate.of(1992, 3, 21),  "cliente100@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Carina Ventura",    "100000101", LocalDate.of(1978, 7, 15),  "cliente101@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Celeste Ramos",     "100000102", LocalDate.of(1988, 5, 19),  "cliente102@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Francisca Torres",  "100000103", LocalDate.of(1996, 12, 1),  "cliente103@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Lara Figueiredo",   "100000104", LocalDate.of(1982, 4, 3),   "cliente104@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Madalena Faria",    "100000105", LocalDate.of(1987, 8, 27),  "cliente105@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Júlia Almeida",     "100000106", LocalDate.of(1975, 2, 16),  "cliente106@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Sónia Teixeira",    "100000107", LocalDate.of(1990, 10, 11), "cliente107@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Cátia Reis",        "100000108", LocalDate.of(1998, 1, 30),  "cliente108@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Teresa Quintas",    "100000109", LocalDate.of(1976, 6, 22),  "cliente109@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Susana Pires",      "100000110", LocalDate.of(1991, 9, 14),  "cliente110@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Catarina Campos",   "100000111", LocalDate.of(1983, 12, 5),  "cliente111@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Francisca Lopes",   "100000112", LocalDate.of(1989, 3, 13),  "cliente112@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Victor Moreira",    "100000113", LocalDate.of(1974, 11, 2),  "cliente113@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Rafael Baptista",   "100000114", LocalDate.of(1981, 7, 25),  "cliente114@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Rodrigo Coelho",    "100000115", LocalDate.of(1993, 5, 8),   "cliente115@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Joel Sequeira",     "100000116", LocalDate.of(1979, 10, 19), "cliente116@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("David Morais",      "100000117", LocalDate.of(1984, 2, 14),  "cliente117@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Bernardo Maia",     "100000118", LocalDate.of(1995, 6, 23),  "cliente118@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Fábio Fonseca",     "100000119", LocalDate.of(1986, 12, 31), "cliente119@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Joaquim Ramalho",   "100000120", LocalDate.of(1977, 4, 7),   "cliente120@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Inácio Mendonça",   "100000121", LocalDate.of(1982, 9, 27),  "cliente121@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Salvador Abreu",    "100000122", LocalDate.of(1976, 1, 26),  "cliente122@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Célio Brito",       "100000123", LocalDate.of(1978, 11, 16), "cliente123@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Afonso Coutinho",   "100000124", LocalDate.of(1992, 3, 2),   "cliente124@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Orlando Gomes",     "100000125", LocalDate.of(1985, 5, 30),  "cliente125@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Vítor Pinto",       "100000126", LocalDate.of(1973, 8, 19),  "cliente126@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Marcos Cordeiro",   "100000127", LocalDate.of(1987, 10, 22), "cliente127@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Paulo Teixeira",    "100000128", LocalDate.of(1975, 12, 13), "cliente128@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Alberto Mendes",    "100000129", LocalDate.of(1981, 9, 9),   "cliente129@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Duarte Dias",       "100000130", LocalDate.of(1994, 7, 18),  "cliente130@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Lucas Martins",     "100000131", LocalDate.of(1990, 2, 25),  "cliente131@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Marcelo Reis",      "100000132", LocalDate.of(1979, 1, 1),   "cliente132@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Nuno Brito",        "100000133", LocalDate.of(1983, 11, 20), "cliente133@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Gustavo Tavares",   "100000134", LocalDate.of(1996, 3, 27),  "cliente134@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Rui Silva",         "100000135", LocalDate.of(1976, 4, 15),  "cliente135@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Paulo Simões",      "100000136", LocalDate.of(1989, 6, 30),  "cliente136@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Manuel Baptista",   "100000137", LocalDate.of(1980, 10, 5),  "cliente137@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Afonso Duarte",     "100000138", LocalDate.of(1995, 8, 8),   "cliente138@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Luís Sequeira",     "100000139", LocalDate.of(1974, 2, 28),  "cliente139@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Miguel Pinto",      "100000140", LocalDate.of(1992, 11, 11), "cliente140@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Jorge Silva",       "100000141", LocalDate.of(1988, 1, 17),  "cliente141@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Fábio Soares",      "100000142", LocalDate.of(1977, 7, 2),   "cliente142@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Carlos Simões",     "100000143", LocalDate.of(1993, 9, 23),  "cliente143@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("António Correia",   "100000144", LocalDate.of(1980, 3, 14),  "cliente144@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Bruno Coutinho",    "100000145", LocalDate.of(1991, 12, 24), "cliente145@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Ricardo Cardoso",   "100000146", LocalDate.of(1984, 6, 6),   "cliente146@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Fernando Nunes",    "100000147", LocalDate.of(1975, 5, 21),  "cliente147@teste.com", "senhadefaultclient"));
        users.add(new UserRegistrationRequest("Mariana Pinto",      "100000198", LocalDate.of(1990, 1, 1), "cliente198@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("João Almeida",       "100000199", LocalDate.of(1990, 1, 1), "cliente199@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Ana Sousa",          "100000200", LocalDate.of(1990, 1, 1), "cliente200@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Pedro Fernandes",    "100000201", LocalDate.of(1990, 1, 1), "cliente201@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Sofia Oliveira",     "100000202", LocalDate.of(1990, 1, 1), "cliente202@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Miguel Silva",       "100000203", LocalDate.of(1990, 1, 1), "cliente203@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Leonor Santos",      "100000204", LocalDate.of(1990, 1, 1), "cliente204@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Inês Costa",         "100000205", LocalDate.of(1990, 1, 1), "cliente205@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Bruno Rodrigues",    "100000206", LocalDate.of(1990, 1, 1), "cliente206@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Beatriz Carvalho",   "100000207", LocalDate.of(1990, 1, 1), "cliente207@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Tiago Lopes",        "100000208", LocalDate.of(1990, 1, 1), "cliente208@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Catarina Teixeira",  "100000209", LocalDate.of(1990, 1, 1), "cliente209@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Francisco Ribeiro",  "100000210", LocalDate.of(1990, 1, 1), "cliente210@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Sara Nascimento",     "100000211", LocalDate.of(1990, 1, 1), "cliente211@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Rafael Araújo",      "100000212", LocalDate.of(1990, 1, 1), "cliente212@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Daniela Marques",     "100000213", LocalDate.of(1990, 1, 1), "cliente213@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("André Soares",       "100000214", LocalDate.of(1990, 1, 1), "cliente214@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Marta Figueiredo",    "100000215", LocalDate.of(1990, 1, 1), "cliente215@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Ricardo Martins",     "100000216", LocalDate.of(1990, 1, 1), "cliente216@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Patrícia Correia",    "100000217", LocalDate.of(1990, 1, 1), "cliente217@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Hugo Batista",        "100000218", LocalDate.of(1990, 1, 1), "cliente218@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Cristina Monteiro",   "100000219", LocalDate.of(1990, 1, 1), "cliente219@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Luís Mendes",         "100000220", LocalDate.of(1990, 1, 1), "cliente220@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Filipa Gonçalves",    "100000221", LocalDate.of(1990, 1, 1), "cliente221@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Vasco Ramos",         "100000222", LocalDate.of(1990, 1, 1), "cliente222@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Filomena Duarte",     "100000223", LocalDate.of(1990, 1, 1), "cliente223@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Marco Nogueira",      "100000224", LocalDate.of(1990, 1, 1), "cliente224@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Luciana Barros",      "100000225", LocalDate.of(1990, 1, 1), "cliente225@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Paulo Reis",          "100000226", LocalDate.of(1990, 1, 1), "cliente226@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Teresa Cardoso",      "100000227", LocalDate.of(1990, 1, 1), "cliente227@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("José Freitas",        "100000228", LocalDate.of(1990, 1, 1), "cliente228@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Rita Moreira",        "100000229", LocalDate.of(1990, 1, 1), "cliente229@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Isabel Rocha",        "100000230", LocalDate.of(1990, 1, 1), "cliente230@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Jorge Machado",       "100000231", LocalDate.of(1990, 1, 1), "cliente231@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Helena Correia",      "100000232", LocalDate.of(1990, 1, 1), "cliente232@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("César Almeida",       "100000233", LocalDate.of(1990, 1, 1), "cliente233@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Vera Paiva",          "100000234", LocalDate.of(1990, 1, 1), "cliente234@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Eduardo Reis",        "100000235", LocalDate.of(1990, 1, 1), "cliente235@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Alice Duarte",        "100000236", LocalDate.of(1990, 1, 1), "cliente236@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Álvaro Castro",       "100000237", LocalDate.of(1990, 1, 1), "cliente237@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Lara Ramos",          "100000238", LocalDate.of(1990, 1, 1), "cliente238@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Henrique Pereira",    "100000239", LocalDate.of(1990, 1, 1), "cliente239@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Cláudia Faria",       "100000240", LocalDate.of(1990, 1, 1), "cliente240@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Bernardo Brito",      "100000241", LocalDate.of(1990, 1, 1), "cliente241@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Letícia Teixeira",    "100000242", LocalDate.of(1990, 1, 1), "cliente242@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Renata Carvalho",     "100000243", LocalDate.of(1990, 1, 1), "cliente243@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Armando Silva",       "100000244", LocalDate.of(1990, 1, 1), "cliente244@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Lucas Barbosa",       "100000245", LocalDate.of(1990, 1, 1), "cliente245@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Salomé Matos",        "100000246", LocalDate.of(1990, 1, 1), "cliente246@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Artur Moreira",       "100000247", LocalDate.of(1990, 1, 1), "cliente247@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Karina Duarte",       "100000248", LocalDate.of(1990, 1, 1), "cliente248@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Dinis Vieira",        "100000249", LocalDate.of(1990, 1, 1), "cliente249@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Juliana Fernandes",   "100000250", LocalDate.of(1990, 1, 1), "cliente250@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Matias Costa",        "100000251", LocalDate.of(1990, 1, 1), "cliente251@teste.com", "senhadefaultuser"));
        users.add(new UserRegistrationRequest("Afonso Abreu",        "100000252", LocalDate.of(1990, 1, 1), "cliente252@teste.com", "senhadefaultuser"));

        return users;
    }

}

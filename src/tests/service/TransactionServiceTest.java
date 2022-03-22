package tests.service;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.Repository;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    TransactionService getTransactionService() {
        Repository<Car> carRepository = new InMemoryRepository<>();
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        Repository<Transaction> transactionRepository = new InMemoryRepository<>();

        CarService carService = new CarService(carRepository, transactionRepository);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, transactionRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);

        carService.add("3", "yaris", 2018, 10000, false);
        carService.add("2", "corolla", 2010, 90000, false);
        carService.add("1", "yaris turbo", 2023, 0, true);
        clientCardService.add("2", "Stefan", "Bertici", "199921", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        clientCardService.add("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        transactionService.add("1", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
        transactionService.add("2", "3", "2", 100d, 50d, LocalDateTime.parse("14.01.2018 12:13", dateTimeFormatter));
        transactionService.add("3", "2", "1", 1000d, 300d, LocalDateTime.parse("15.02.2022 21:11", dateTimeFormatter));
        transactionService.add("4", "1", "", 50d, 100d, LocalDateTime.parse("25.03.2022 15:13", dateTimeFormatter));

        return transactionService;
    }

    @Test
    void add() {
        TransactionService transactionService = getTransactionService();

        try {
            transactionService.add("1", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is an entity with id 1", ipex.getMessage());
        }

        try {
            transactionService.add("99", "2", "1", 1000d, 100d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void getAll() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactions = transactionService.getAll();
        assertEquals(4, transactions.size());

        transactionService.add("99", "2", "1", 1000d, 100d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
        transactions = transactionService.getAll();
        assertEquals(5, transactions.size());
    }

    @Test
    void get() {
        TransactionService transactionService = getTransactionService();

        assertEquals("1", transactionService.get("1").getId());
        assertEquals("3", transactionService.get("2").getCarId());
        assertEquals("1", transactionService.get("3").getClientCardId());
        assertEquals(0d, transactionService.get("4").getPartPrice());       // 0 because of warranty
        assertEquals(495d, transactionService.get("1").getLaborPrice());    // -10% because client card
        assertEquals("14.01.2018 12:13", transactionService.get("2").getDateAndTime().format(dateTimeFormatter));
    }

    @Test
    void getInvoice() {
        TransactionService transactionService = getTransactionService();
        Invoice firstInvoice = transactionService.getInvoice("1", "", 50d, 100d);
        Invoice secondInvoice = transactionService.getInvoice("2", "2", 200d, 100d);

        assertEquals(0, firstInvoice.getFinalPartPrice());
        assertEquals(100d, firstInvoice.getFinalLaborPrice());
        assertEquals(200, secondInvoice.getFinalPartPrice());
        assertEquals(90d, secondInvoice.getFinalLaborPrice());
    }

    @Test
    void update() {
        TransactionService transactionService = getTransactionService();

        try {
            transactionService.update("99", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id 99", ipex.getMessage());
        }

        try {
            transactionService.update("1", "1", "1", 1000d, 100d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
            assertEquals(0d, transactionService.get("1").getPartPrice());
            assertEquals(90d, transactionService.get("1").getLaborPrice());
            transactionService.update("4", "2", "", 50d, 100d, LocalDateTime.parse("25.03.2022 15:13", dateTimeFormatter));
            assertEquals(50d, transactionService.get("4").getPartPrice());
            assertEquals(100d, transactionService.get("4").getLaborPrice());
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void delete() {
        TransactionService transactionService = getTransactionService();

        String idOne = "1";
        String idNinetyNine = "99";

        try {
            transactionService.delete(idNinetyNine);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id " + idNinetyNine, ipex.getMessage());
        }

        try {
            transactionService.delete(idOne);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void deleteTransactionsBetweenGivenBounds() {
        TransactionService transactionService = getTransactionService();
        int deletedCount = transactionService.deleteTransactionsBetweenGivenBounds(LocalDateTime.parse("21.07.2012 09:50", dateTimeFormatter), LocalDateTime.parse("21.07.2015 09:50", dateTimeFormatter));
        assertEquals(0, deletedCount);

        deletedCount = transactionService.deleteTransactionsBetweenGivenBounds(LocalDateTime.parse("01.01.2018 09:50", dateTimeFormatter), LocalDateTime.parse("21.07.2023 09:50", dateTimeFormatter));
        assertEquals(3, deletedCount);
    }

    @Test
    void getTransactionsBetweenBounds() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactions = transactionService.getTransactionsBetweenBounds(0d, 200);
        assertEquals(2, transactions.size());

        transactions = transactionService.getTransactionsBetweenBounds(200d, 1500);
        assertEquals(1, transactions.size());
    }
}
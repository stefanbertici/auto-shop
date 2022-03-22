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

class ClientCardServiceTest {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    ClientCardService getClientCardService() {
        Repository<Car> carRepository = new InMemoryRepository<>();
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        Repository<Transaction> transactionRepository = new InMemoryRepository<>();

        CarService carService = new CarService(carRepository, transactionRepository);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, transactionRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);

        carService.add("3", "yaris", 2018, 10000, true);
        carService.add("2", "corolla", 2010, 90000, false);
        carService.add("1", "yaris turbo", 2023, 0, true);
        carService.add("4", "update test car #1", 2001, 50000, true);
        carService.add("5", "update test car #2", 2021, 80000, true);
        clientCardService.add("2", "Stefan", "Bertici", "199921", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        clientCardService.add("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        transactionService.add("1", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
        transactionService.add("2", "3", "2", 100d, 50d, LocalDateTime.parse("14.01.2018 12:13", dateTimeFormatter));
        transactionService.add("3", "2", "1", 1000d, 300d, LocalDateTime.parse("15.02.2022 21:11", dateTimeFormatter));
        transactionService.add("4", "1", "", 50d, 100d, LocalDateTime.parse("25.03.2022 15:13", dateTimeFormatter));

        return clientCardService;
    }

    @Test
    void add() {
        ClientCardService clientCardService = getClientCardService();

        try {
            clientCardService.add("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is an entity with id 1", ipex.getMessage());
        }

        try {
            clientCardService.add("99", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void getAll() {
        ClientCardService clientCardService = getClientCardService();
        List<ClientCard> cards = clientCardService.getAll();
        assertEquals(2, cards.size());

        clientCardService.add("99", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        cards = clientCardService.getAll();
        assertEquals(3, cards.size());
    }

    @Test
    void get() {
        ClientCardService clientCardService = getClientCardService();

        assertEquals("2", clientCardService.get("2").getId());
        assertEquals("Stefan", clientCardService.get("2").getFirstName());
        assertEquals("Bertici", clientCardService.get("2").getLastName());
        assertEquals("199922", clientCardService.get("1").getCnp());
        assertEquals("12.03.1993", clientCardService.get("1").getBirthDate().format(dateFormatter));
        assertEquals("01.01.2022", clientCardService.get("1").getRegistrationDate().format(dateFormatter));
    }

    @Test
    void update() {
        ClientCardService clientCardService = getClientCardService();

        try {
            clientCardService.update("99", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id 99", ipex.getMessage());
        }

        try {
            clientCardService.resetCnpInCaseItDoesNotChangeAtUpdate("2");
            clientCardService.update("2", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void resetCnpInCaseItDoesNotChangeAtUpdate() {
        ClientCardService clientCardService = getClientCardService();

        assertEquals("199922", clientCardService.get("1").getCnp());
        clientCardService.resetCnpInCaseItDoesNotChangeAtUpdate("1");
        assertEquals("-1", clientCardService.get("1").getCnp());
    }

    @Test
    void delete() {
        ClientCardService clientCardService = getClientCardService();

        String idOne = "1";
        String idNinetyNine = "99";

        try {
            clientCardService.delete(idNinetyNine);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id " + idNinetyNine, ipex.getMessage());
        }

        try {
            clientCardService.delete(idOne);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void getClientCardsFullTextSearch() {
        ClientCardService clientCardService = getClientCardService();
        String searchTerm = "yar";

        List<ClientCard> searchResult = clientCardService.getClientCardsFullTextSearch(searchTerm, dateFormatter);
        assertEquals(1, searchResult.size());

        searchTerm = "999";
        searchResult = clientCardService.getClientCardsFullTextSearch(searchTerm, dateFormatter);
        assertEquals(2, searchResult.size());
    }

    @Test
    void getClientCardsOrderedDescendingBySumOfDiscounts() {
        ClientCardService clientCardService = getClientCardService();
        List<ClientCardWithSumOfDiscounts> clientCardsOrderedBySumOfDiscounts = clientCardService.getClientCardsOrderedDescendingBySumOfDiscounts();

        assertEquals("2", clientCardsOrderedBySumOfDiscounts.get(0).getClientCard().getId());
        assertEquals("1", clientCardsOrderedBySumOfDiscounts.get(1).getClientCard().getId());
    }
}
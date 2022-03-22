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

class CarServiceTest {

    CarService getCarService() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

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

        return carService;
    }

    @Test
    void add() {
        CarService carService = getCarService();

        try {
            carService.add("1", "yaris turbo", 2023, 0, true);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is an entity with id 1", ipex.getMessage());
        }

        try {
            carService.add("99", "yaris turbo", 2023, 0, true);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void getAll() {
        CarService carService = getCarService();
        List<Car> cars = carService.getAll();
        assertEquals(5, cars.size());

        carService.add("99", "yaris turbo", 2023, 0, true);
        cars = carService.getAll();
        assertEquals(6, cars.size());
    }

    @Test
    void get() {
        CarService carService = getCarService();

        assertEquals("1", carService.get("1").getId());
        assertEquals("corolla", carService.get("2").getModel());
        assertEquals(2018, carService.get("3").getYearOfPurchase());
        assertEquals(50000, carService.get("4").getKm());
        assertTrue(carService.get("5").isWarranty());
    }

    @Test
    void update() {
        CarService carService = getCarService();

        try {
            carService.update("99", "yaris turbo", 2023, 0, true);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id 99", ipex.getMessage());
        }

        try {
            carService.update("1", "new yaris turbo", 2023, 0, true);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void updateAllCarWarranties() {
        CarService carService = getCarService();

        int count = carService.updateAllCarWarranties();
        assertEquals(3, count);
    }

    @Test
    void delete() {
        CarService carService = getCarService();

        String idOne = "1";
        String idNinetyNine = "99";

        try {
            carService.delete(idNinetyNine);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id " + idNinetyNine, ipex.getMessage());
        }

        try {
            carService.delete(idOne);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void getCarsFullTextSearch() {
        CarService carService = getCarService();
        String searchTerm = "yari";

        List<Car> searchResult = carService.getCarsFullTextSearch(searchTerm);
        assertEquals(2, searchResult.size());

        searchTerm = "1";
        searchResult = carService.getCarsFullTextSearch(searchTerm);
        assertEquals(5, searchResult.size());
    }

    @Test
    void getCarsOrderedDescendingBySumOfLaborPrice() {
        CarService carService = getCarService();
        List<CarWithSumOfLaborPrice> carsOrderedBySumOfLabor = carService.getCarsOrderedDescendingBySumOfLaborPrice();

        assertEquals("2", carsOrderedBySumOfLabor.get(0).getCar().getId());
        assertEquals("1", carsOrderedBySumOfLabor.get(1).getCar().getId());
        assertEquals("3", carsOrderedBySumOfLabor.get(2).getCar().getId());
    }
}
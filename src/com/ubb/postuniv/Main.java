package com.ubb.postuniv;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.*;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;
import com.ubb.postuniv.userInterface.TextUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

        Repository<Car> carRepository = new InMemoryRepository<>();
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        Repository<Transaction> transactionRepository = new InMemoryRepository<>();

        CarService carService = new CarService(carRepository);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);

        CarValidator carValidator = new CarValidator(carRepository);
        ClientCardValidator clientCardValidator = new ClientCardValidator(clientCardRepository);
        TransactionValidator transactionValidator = new TransactionValidator(transactionRepository, carRepository, clientCardRepository);

        TextUI ui = new TextUI(carService, clientCardService, transactionService, carValidator, clientCardValidator, transactionValidator, dateFormatter, dateTimeFormatter);

        carService.add("3", "yaris", 2022, 1000, true);
        carService.add("2", "corolla", 2010, 90000, false);
        carService.add("1", "yaris turbo", 2023, 0, true);
        clientCardService.add("2", "Stefan", "Bertici", "199921", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        clientCardService.add("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));

        ui.start();
    }
}

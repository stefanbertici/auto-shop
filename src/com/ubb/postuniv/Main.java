package com.ubb.postuniv;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.*;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;
import com.ubb.postuniv.userInterface.TextUI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

        Repository<Car> carRepository = new InMemoryRepository<>();
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        Repository<Transaction> transactionRepository = new InMemoryRepository<>();

        CarService carService = new CarService(carRepository, transactionRepository);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, transactionRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);

        CarValidator carValidator = new CarValidator(carRepository);
        ClientCardValidator clientCardValidator = new ClientCardValidator(clientCardRepository);
        TransactionValidator transactionValidator = new TransactionValidator(transactionRepository, carRepository, clientCardRepository);

        TextUI ui = new TextUI(carService, clientCardService, transactionService, carValidator, clientCardValidator, transactionValidator, dateFormatter, dateTimeFormatter);

        carService.add("3", "yaris", 2018, 10000, true);
        carService.add("2", "corolla", 2010, 90000, false);
        carService.add("1", "yaris turbo", 2023, 0, true);
        clientCardService.add("2", "Stefan", "Bertici", "199921", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        clientCardService.add("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        transactionService.add("1", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter));
        transactionService.add("2", "3", "2", 100d, 50d, LocalDateTime.parse("14.01.2018 12:13", dateTimeFormatter));
        transactionService.add("3", "2", "1", 1000d, 300d, LocalDateTime.parse("15.02.2022 21:11", dateTimeFormatter));
        transactionService.add("4", "1", "", 50d, 100d, LocalDateTime.parse("25.03.2022 15:13", dateTimeFormatter));

        ui.start();
    }
}

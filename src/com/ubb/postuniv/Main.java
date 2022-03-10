package com.ubb.postuniv;

import com.ubb.postuniv.domain.CarValidator;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.domain.TransactionValidator;
import com.ubb.postuniv.repository.CarRepository;
import com.ubb.postuniv.repository.ClientCardRepository;
import com.ubb.postuniv.repository.TransactionRepository;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;
import com.ubb.postuniv.userInterface.TextUI;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        formatter.withResolverStyle(ResolverStyle.STRICT);

        CarRepository carRepository = new CarRepository();
        ClientCardRepository clientCardRepository = new ClientCardRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        CarService carService = new CarService(carRepository);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);

        CarValidator carValidator = new CarValidator(carRepository);
        ClientCardValidator clientCardValidator = new ClientCardValidator(clientCardRepository);
        TransactionValidator transactionValidator = new TransactionValidator(transactionRepository);

        TextUI ui = new TextUI(carService, clientCardService, transactionService, carValidator, clientCardValidator, transactionValidator, formatter);

        carService.add("1", "yaris", 2020, 1000, true);
        carService.add("2", "corolla", 2010, 90000, false);

        /*
        LocalDate birthDate = LocalDate.parse("12.03.1993", formatter);
        LocalDate regDate = LocalDate.parse("01.01.2019", formatter);
        clientCardService.add("1", "Stefan" , "Bertici", "109329382477", birthDate, regDate);

        transactionService.add("1", "1", "", 1000, 500, LocalDateTime.now());
        transactionService.add("2", "2", "1", 1000, 500, LocalDateTime.now());

        System.out.println(carService.getAll());
        System.out.println(clientCardService.getAll());
        System.out.println(transactionService.getAll());
        */

        ui.start();
    }
}

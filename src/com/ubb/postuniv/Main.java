package com.ubb.postuniv;

import com.ubb.postuniv.repository.CarRepository;
import com.ubb.postuniv.repository.ClientCardRepository;
import com.ubb.postuniv.repository.TransactionRepository;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Main {
    public static void main(String[] args) {
        CarRepository carRepo = new CarRepository();
        ClientCardRepository clientCardRepo = new ClientCardRepository();
        TransactionRepository transactionRepo = new TransactionRepository();

        CarService carService = new CarService(carRepo);
        ClientCardService cardService = new ClientCardService(clientCardRepo);
        TransactionService transactionService = new TransactionService(transactionRepo, carRepo);


        carService.add("1", "yaris", 2020, 1000, true);
        carService.add("2", "corolla", 2010, 90000, false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        formatter.withResolverStyle(ResolverStyle.STRICT);

        LocalDate birthDate = LocalDate.parse("12.03.1993", formatter);
        LocalDate regDate = LocalDate.parse("01.01.2019", formatter);
        cardService.add("1", "Stefan" , "Bertici", "109329382477", birthDate, regDate);

        transactionService.add("1", "1", "", 1000, 500, LocalDateTime.now());
        transactionService.add("2", "2", "1", 1000, 500, LocalDateTime.now());

        System.out.println(carService.getAll());
        System.out.println(cardService.getAll());
        System.out.println(transactionService.getAll());
    }
}

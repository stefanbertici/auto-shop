package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.CarWithTotalLaborPrice;
import com.ubb.postuniv.domain.Invoice;
import com.ubb.postuniv.domain.Transaction;
import com.ubb.postuniv.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {
    private Repository<Transaction> transactionRepository;
    private Repository<Car> carRepository;

    public TransactionService(Repository<Transaction> transactionRepository, Repository<Car> carRepository) {
        this.transactionRepository = transactionRepository;
        this.carRepository = carRepository;
    }

    //add
    public void add(String id, String carId, String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime) throws RuntimeException{
        double finalPartPrice, finalLaborPrice;

        if (carRepository.read(carId).isWarranty()) {
            finalPartPrice = 0;
        } else {
            finalPartPrice = partPrice;
        }

        if (!clientCardId.equals("")) {
            finalLaborPrice = laborPrice * 0.9;
        } else {
            finalLaborPrice = laborPrice;
        }

        Transaction transaction = new Transaction(id, carId, clientCardId, finalPartPrice, finalLaborPrice, dateAndTime);
        transactionRepository.create(transaction);
    }

    //get all ordered by id
    public List<Transaction> getAll() {
        return transactionRepository.readAll()
                .stream()
                .sorted(Comparator.comparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    //get a car by id
    public Transaction get(String id) {
        return transactionRepository.read(id);
    }

    public Invoice getInvoice(String carId, String clientCardId, double partPrice, double laborPrice) {
        double finalPartPrice, finalLaborPrice;
        double partDiscount, laborDiscount;

        if (carRepository.read(carId).isWarranty()) {
            finalPartPrice = 0;
            partDiscount = partPrice;
        } else {
            finalPartPrice = partPrice;
            partDiscount = 0;
        }

        if (!clientCardId.equals("")) {
            finalLaborPrice = laborPrice * 0.9;
            laborDiscount = laborPrice - finalLaborPrice;
        } else {
            finalLaborPrice = laborPrice;
            laborDiscount = 0;
        }

        return new Invoice(partPrice, laborPrice, partDiscount, laborDiscount, finalPartPrice, finalLaborPrice);
    }

    //update
    public void update(String id, String carId, String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime) throws RuntimeException{
        double laborPriceWithDiscount = laborPrice;
        if (!clientCardId.equals("")) {
            laborPriceWithDiscount = laborPrice * 0.9;
        }

        double partPriceWithDiscount = partPrice;
        if (carRepository.read(carId).isWarranty()) {
            partPriceWithDiscount = 0;
        }

        Transaction transaction = new Transaction(id, carId, clientCardId, partPriceWithDiscount, laborPriceWithDiscount, dateAndTime);
        transactionRepository.update(transaction);

        System.out.println("Total = " + (laborPriceWithDiscount + partPriceWithDiscount) + ", discount = " + (laborPrice - laborPriceWithDiscount + partPrice - partPriceWithDiscount));
    }

    //delete
    public void delete(String id) throws RuntimeException {
        transactionRepository.delete(id);
    }

    public List<Transaction> getAllTransactionsBetweenBounds(double lower, double upper) {
        return transactionRepository.readAll()
                .stream()
                .filter(t -> t.getLaborPrice() + t.getPartPrice() >= lower)
                .filter(t -> t.getLaborPrice() + t.getPartPrice() <= upper)
                .collect(Collectors.toList());
    }

    public List<CarWithTotalLaborPrice> getAllCarsOrderedDescendingBySumOfLaborPrice() {
        Map<String, Double> carIdAndLaborGroupings = transactionRepository.readAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCarId, Collectors.summingDouble(Transaction::getLaborPrice)));

        return carIdAndLaborGroupings.entrySet()
                .stream()
                .map(e -> new CarWithTotalLaborPrice(carRepository.read(e.getKey()), e.getValue()))
                .sorted(Comparator.comparingDouble(CarWithTotalLaborPrice::getTotalLaborPrice).reversed())
                .collect(Collectors.toList());
    }
}

package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Transaction;
import com.ubb.postuniv.repository.CarRepository;
import com.ubb.postuniv.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository;
    private CarRepository carRepository;

    public TransactionService(TransactionRepository transactionRepository, CarRepository carRepository) {
        this.transactionRepository = transactionRepository;
        this.carRepository = carRepository;
    }

    //add
    public void add(String id, String carId, String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime) throws RuntimeException{
        double laborPriceWithDiscount = laborPrice;
        if (!clientCardId.equals("")) {
            laborPriceWithDiscount = laborPrice * 0.9;
        }

        double partPriceWithDiscount = partPrice;
        if (carRepository.read(carId).isWarranty()) {
            partPriceWithDiscount = 0;
        }

        Transaction transaction = new Transaction(id, carId, clientCardId, partPriceWithDiscount, laborPriceWithDiscount, dateAndTime);
        transactionRepository.create(transaction);

        System.out.println("Total = " + (laborPriceWithDiscount + partPriceWithDiscount) + ", discount = " + (laborPrice - laborPriceWithDiscount + partPrice - partPriceWithDiscount));
    }

    //get all ordered by id
    public List<Transaction> getAll() {
        List<Transaction> transactionsById = new ArrayList<>(transactionRepository.readAll());
        transactionsById.sort(new Comparator<>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        return transactionsById;
    }

    //get a car by id
    public Transaction get(String id) {
        return transactionRepository.read(id);
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
}

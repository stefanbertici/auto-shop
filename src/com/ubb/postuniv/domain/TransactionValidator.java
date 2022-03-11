package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.CarRepository;
import com.ubb.postuniv.repository.ClientCardRepository;
import com.ubb.postuniv.repository.TransactionRepository;

public class TransactionValidator {
    private TransactionRepository transactionRepository;
    private CarRepository carRepository;
    private ClientCardRepository clientCardRepository;

    public TransactionValidator(TransactionRepository transactionRepository, CarRepository carRepository, ClientCardRepository clientCardRepository) {
        this.transactionRepository = transactionRepository;
        this.carRepository = carRepository;
        this.clientCardRepository = clientCardRepository;
    }

    public void validateIdForUpdate(String id) throws RuntimeException {
        Transaction transaction = transactionRepository.read(id);
        if (transaction == null) {
            throw new RuntimeException("Error: There is no transaction with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws RuntimeException {
        Transaction transaction = transactionRepository.read(id);
        if (transaction != null) {
            throw new RuntimeException("Error: There already is a transaction with id " + id);
        }
    }

    public void validateCarId(String id) throws RuntimeException {
        Car car = carRepository.read(id);
        if (car == null) {
            throw new RuntimeException("Error: There is no car with id " + id);
        }
    }

    public  void validateClientCardIdWhichCanBeNull(String id) throws RuntimeException {
        if (id.equals("")) { // valid input
            return;
        }

        ClientCard clientCard = clientCardRepository.readById(id);
        if (clientCard == null) {
            throw new RuntimeException("Error: There is no client card with id " + id);
        }
    }
}

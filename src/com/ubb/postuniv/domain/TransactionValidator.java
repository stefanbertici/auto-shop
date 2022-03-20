package com.ubb.postuniv.domain;

import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.repository.Repository;

public class TransactionValidator {
    private final Repository<Transaction> transactionRepository;
    private final Repository<Car> carRepository;
    private final Repository<ClientCard> clientCardRepository;

    public TransactionValidator(Repository<Transaction> transactionRepository, Repository<Car> carRepository, Repository<ClientCard> clientCardRepository) {
        this.transactionRepository = transactionRepository;
        this.carRepository = carRepository;
        this.clientCardRepository = clientCardRepository;
    }

    public void validateIdForUpdate(String id) throws IdProblemException {
        Transaction transaction = transactionRepository.read(id);
        if (transaction == null) {
            throw new IdProblemException("Error: There is no transaction with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws IdProblemException {
        Transaction transaction = transactionRepository.read(id);
        if (transaction != null) {
            throw new IdProblemException("Error: There already is a transaction with id " + id);
        }
    }

    public void validateCarId(String id) throws IdProblemException {
        Car car = carRepository.read(id);
        if (car == null) {
            throw new IdProblemException("Error: There is no car with id " + id);
        }
    }

    public  void validateClientCardIdWhichCanBeNull(String id) throws IdProblemException {
        if (id.equals("")) { // valid input
            return;
        }

        ClientCard clientCard = clientCardRepository.read(id);
        if (clientCard == null) {
            throw new IdProblemException("Error: There is no client card with id " + id);
        }
    }
}

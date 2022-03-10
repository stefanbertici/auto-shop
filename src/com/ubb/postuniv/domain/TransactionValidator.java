package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.CarRepository;
import com.ubb.postuniv.repository.TransactionRepository;

public class TransactionValidator {
    private TransactionRepository transactionRepository;

    public TransactionValidator(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}

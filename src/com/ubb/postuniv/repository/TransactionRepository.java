package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private Map<String, Transaction> transactions;

    public TransactionRepository() {
        this.transactions = new HashMap<>();
    }

    //create
    public void create(Transaction transaction) throws RuntimeException {
        if (transactions.containsKey(transaction.getId())) {
            throw new RuntimeException("Error: There already is a transaction with id " + transaction.getId());
        } else {
            transactions.put(transaction.getId(), transaction);
        }
    }

    //read
    public List<Transaction> readAll() {
        return new ArrayList<>(transactions.values());
    }

    public Transaction read(String id) {
        return transactions.getOrDefault(id, null);
    }

    //update
    public void update(Transaction transaction) throws RuntimeException {
        if (transactions.containsKey(transaction.getId())) {
            transactions.put(transaction.getId(), transaction);
        } else {
            throw new RuntimeException("Error: There is no transaction with id " + transaction.getId());
        }
    }

    //delete
    public void delete(String id) throws RuntimeException {
        if (transactions.containsKey(id)) {
            transactions.remove(id);
        } else {
            throw new RuntimeException("Error: There is no transaction with id " + id);
        }
    }
}

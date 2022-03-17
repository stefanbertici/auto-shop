package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardWithSumOfDiscounts;
import com.ubb.postuniv.domain.Transaction;
import com.ubb.postuniv.repository.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientCardService {
    private Repository<ClientCard> clientCardRepository;
    private Repository<Transaction> transactionRepository;

    public ClientCardService(Repository<ClientCard> clientCardRepository, Repository<Transaction> transactionRepository) {
        this.clientCardRepository = clientCardRepository;
        this.transactionRepository = transactionRepository;
    }

    //add
    public void add(String id, String firstName, String lastName, String cnp, LocalDate birthDate, LocalDate registrationDate) throws RuntimeException{
        ClientCard card = new ClientCard(id, firstName, lastName, cnp, birthDate, registrationDate);
        clientCardRepository.create(card);
    }

    //get all ordered by id
    public List<ClientCard> getAll() {
        return clientCardRepository.readAll()
                .stream()
                .sorted(Comparator.comparing(ClientCard::getId))
                .collect(Collectors.toList());
    }

    //get a card by id
    public ClientCard get(String id) {
        return clientCardRepository.read(id);
    }

    //update
    public void update(String id, String firstName, String lastName, String cnp, LocalDate birthDate, LocalDate registrationDate) throws RuntimeException{
        ClientCard card = new ClientCard(id, firstName, lastName, cnp, birthDate, registrationDate);
        clientCardRepository.update(card);
    }

    public void resetCnpInCaseItDoesNotChangeAtUpdate(String id) {
        ClientCard card = clientCardRepository.read(id);
        card.setCnp("-1");
        clientCardRepository.update(card);
    }

    //delete
    public void delete(String id) throws RuntimeException {
        clientCardRepository.delete(id);
    }

    //reports
    public List<ClientCard> getClientCardsFullTextSearch(String searchTerm, DateTimeFormatter dateFormatter) {
        return clientCardRepository.readAll()
                .stream()
                .filter(clientCard -> clientCard.getId().contains(searchTerm) ||
                        clientCard.getFirstName().toLowerCase().contains(searchTerm) ||
                        clientCard.getLastName().toLowerCase().contains(searchTerm) ||
                        clientCard.getCnp().contains(searchTerm) ||
                        clientCard.getBirthDate().format(dateFormatter).contains(searchTerm) ||
                        clientCard.getRegistrationDate().format(dateFormatter).contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<ClientCardWithSumOfDiscounts> getClientCardsOrderedDescendingBySumOfDiscounts() {
        Map<String, Double> clientCardIdAndDiscountsGrouping = transactionRepository.readAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getClientCardId, Collectors.summingDouble(Transaction::getAppliedDiscount)));

        return clientCardIdAndDiscountsGrouping.entrySet()
                .stream()
                .map(e -> new ClientCardWithSumOfDiscounts(clientCardRepository.read(e.getKey()), e.getValue()))
                .filter(c -> c.getClientCard() != null)
                .sorted(Comparator.comparingDouble(ClientCardWithSumOfDiscounts::getSumOfDiscounts).reversed())
                .collect(Collectors.toList());
    }
}

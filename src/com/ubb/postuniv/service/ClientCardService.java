package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.repository.ClientCardRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientCardService {
    private ClientCardRepository cardRepository;

    public ClientCardService(ClientCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    //add
    public void add(String id, String firstName, String lastName, String cnp, String birthDate, String registrationDate) throws RuntimeException{
        ClientCard card = new ClientCard(id, firstName, lastName, cnp, birthDate, registrationDate);
        cardRepository.create(card);
    }

    //get all ordered by id
    public List<ClientCard> getAll() {
        List<ClientCard> cardsById = new ArrayList<>(cardRepository.readAll());
        cardsById.sort(new Comparator<>() {
            @Override
            public int compare(ClientCard o1, ClientCard o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        return cardsById;
    }

    //get a card by id
    public ClientCard get(String id) {
        return cardRepository.read(id);
    }

    //update
    public void update(String id, String firstName, String lastName, String cnp, String birthDate, String registrationDate) throws RuntimeException{
        ClientCard card = new ClientCard(id, firstName, lastName, cnp, birthDate, registrationDate);
        cardRepository.update(card);
    }

    //delete
    public void delete(String id) throws RuntimeException {
        cardRepository.delete(id);
    }
}

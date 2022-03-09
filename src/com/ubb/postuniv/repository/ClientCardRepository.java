package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.ClientCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCardRepository {
    private Map<String, ClientCard> cards;

    public ClientCardRepository() {
        this.cards = new HashMap<>();
    }

    //create
    public void create(ClientCard card) throws RuntimeException {
        if (cards.containsKey(card.getId())) {
            throw new RuntimeException("Error: There already is a card with id " + card.getId());
        } else {
            cards.put(card.getId(), card);
        }
    }

    //read
    public List<ClientCard> readAll() {
        return new ArrayList<>(cards.values());
    }

    public ClientCard read(String id) {
        return cards.getOrDefault(id, null);
    }

    //update
    public void update(ClientCard card) throws RuntimeException {
        if (cards.containsKey(card.getId())) {
            cards.put(card.getId(), card);
        } else {
            throw new RuntimeException("Error: There is no card with id " + card.getId());
        }
    }

    //delete
    public void delete(String id) throws RuntimeException {
        if (cards.containsKey(id)) {
            cards.remove(id);
        } else {
            throw new RuntimeException("Error: There is no card with id " + id);
        }
    }
}

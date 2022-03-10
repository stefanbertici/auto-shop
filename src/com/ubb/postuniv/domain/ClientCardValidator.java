package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.ClientCardRepository;

public class ClientCardValidator {
    private ClientCardRepository cardRepository;

    public ClientCardValidator(ClientCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void validateIdForUpdate(String id) throws RuntimeException {
        ClientCard card = cardRepository.readById(id);
        if (card == null) {
            throw new RuntimeException("Error: There is no card with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws RuntimeException {
        ClientCard card = cardRepository.readById(id);
        if (card != null) {
            throw new RuntimeException("Error: There already is a card with id " + id);
        }
    }

    public void validateCnpForUpdate(String cnp) throws RuntimeException {
        ClientCard card = cardRepository.readByCnp(cnp);
        if (card == null) {
            throw new RuntimeException("Error: There is no card with cnp " + cnp);
        }
    }

    public void validateCnpForAdd(String cnp) throws RuntimeException {
        ClientCard card = cardRepository.readById(cnp);
        if (card != null) {
            throw new RuntimeException("Error: There already is a card with cnp " + cnp);
        }
    }
}

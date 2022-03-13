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

    public void validateCnp(String cnp) throws RuntimeException {
        ClientCard card = cardRepository.readByCnp(cnp);
        if (cnp.equals("")) {
            throw new RuntimeException("Error: CNP cannot be empty string.");
        } else if (card != null) {
            throw new RuntimeException("Error: There already is a card with cnp " + cnp);
        }
    }

    public void validateNameFormat(String name) {
        if (name.equals("")) {
            throw new RuntimeException("Error: Name cannot be empty string.");
        }
    }
}

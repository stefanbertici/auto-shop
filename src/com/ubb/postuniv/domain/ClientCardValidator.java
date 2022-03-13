package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.Repository;

public class ClientCardValidator {
    private Repository<ClientCard> cardRepository;

    public ClientCardValidator(Repository<ClientCard> cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void validateIdForUpdate(String id) throws RuntimeException {
        ClientCard card = cardRepository.read(id);
        if (card == null) {
            throw new RuntimeException("Error: There is no card with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws RuntimeException {
        ClientCard card = cardRepository.read(id);
        if (card != null) {
            throw new RuntimeException("Error: There already is a card with id " + id);
        }
    }

    public void validateCnp(String cnp) throws RuntimeException {
        if (cnp.equals("")) {
            throw new RuntimeException("Error: CNP cannot be empty string.");
        }

        long numberOfCardsWithGivenCnp = cardRepository.readAll()
                .stream()
                .filter(e -> e.getCnp().equals(cnp))
                .count();

        if (numberOfCardsWithGivenCnp != 0L) {
            throw new RuntimeException("Error: There already is a card with cnp " + cnp);
        }
    }

    public void validateNameFormat(String name) {
        if (name.equals("")) {
            throw new RuntimeException("Error: Name cannot be empty string.");
        }
    }
}

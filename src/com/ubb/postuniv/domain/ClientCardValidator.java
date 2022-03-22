package com.ubb.postuniv.domain;

import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.exceptions.StringFormatException;
import com.ubb.postuniv.repository.Repository;

public class ClientCardValidator {
    private final Repository<ClientCard> cardRepository;

    public ClientCardValidator(Repository<ClientCard> cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void validateIdForUpdate(String id) throws IdProblemException {
        ClientCard card = cardRepository.read(id);
        if (card == null) {
            throw new IdProblemException("Error: There is no card with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws IdProblemException {
        ClientCard card = cardRepository.read(id);
        if (card != null) {
            throw new IdProblemException("Error: There already is a card with id " + id);
        }
    }

    public void validateCnp(String cnp) throws StringFormatException, IdProblemException {
        if (cnp.isBlank()) {
            throw new StringFormatException("Error: CNP cannot be empty string.");
        }

        long numberOfCardsWithGivenCnp = cardRepository.readAll()
                .stream()
                .filter(e -> e.getCnp().equals(cnp))
                .count();

        if (numberOfCardsWithGivenCnp != 0L) {
            throw new IdProblemException("Error: There already is a card with cnp " + cnp);
        }
    }

    public void validateNameFormat(String name) throws StringFormatException {
        if (name.isBlank()) {
            throw new StringFormatException("Error: Name cannot be empty string.");
        }
    }
}

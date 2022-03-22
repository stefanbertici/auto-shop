package tests.domain;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.exceptions.StringFormatException;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.Repository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardValidatorTest {

    ClientCardValidator getClientCardValidator() {
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        ClientCard clientCard = new ClientCard("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        clientCardRepository.create(clientCard);

        return new ClientCardValidator(clientCardRepository);
    }

    @Test
    void validateIdForUpdate() {
        ClientCardValidator clientCardValidator = getClientCardValidator();
        String id = "2";

        try {
            clientCardValidator.validateIdForUpdate(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no card with id " + id, ipex.getMessage());
        }

        id = "1";

        try {
            clientCardValidator.validateIdForUpdate(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateIdForAdd() {
        ClientCardValidator clientCardValidator = getClientCardValidator();
        String id = "1";

        try {
            clientCardValidator.validateIdForAdd(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is a card with id " + id, ipex.getMessage());
        }

        id = "2";

        try {
            clientCardValidator.validateIdForAdd(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateCnp() {
        ClientCardValidator clientCardValidator = getClientCardValidator();
        String cnp = "";

        try {
            clientCardValidator.validateCnp(cnp);
            fail();
        } catch (StringFormatException sfex) {
            assertEquals("Error: CNP cannot be empty string.", sfex.getMessage());
        }

        cnp = "199922";

        try {
            clientCardValidator.validateCnp(cnp);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is a card with cnp " + cnp, ipex.getMessage());
        }

        cnp = "199923";

        try {
            clientCardValidator.validateCnp(cnp);
        } catch (IdProblemException ipex) {
            fail();
        }

    }

    @Test
    void validateNameFormat() {
        ClientCardValidator clientCardValidator = getClientCardValidator();
        String name = "";

        try {
            clientCardValidator.validateNameFormat(name);
            fail();
        } catch (StringFormatException sfex) {
            assertEquals("Error: Name cannot be empty string.", sfex.getMessage());
        }

        name = "John";

        try {
            clientCardValidator.validateNameFormat(name);
        } catch (StringFormatException sfex) {
            fail();
        }
    }
}
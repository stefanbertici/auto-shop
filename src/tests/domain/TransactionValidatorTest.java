package tests.domain;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.Repository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    TransactionValidator getTransactionValidator() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
        Car car = new Car("1", "yaris turbo", 2023, 0, true);
        ClientCard clientCard = new ClientCard("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
        Transaction transaction = new Transaction("1", "1", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter), 100d);

        Repository<Car> carRepository = new InMemoryRepository<>();
        Repository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        Repository<Transaction> transactionRepository = new InMemoryRepository<>();

        carRepository.create(car);
        clientCardRepository.create(clientCard);
        transactionRepository.create(transaction);

        return new TransactionValidator(transactionRepository, carRepository, clientCardRepository);
    }

    @Test
    void validateIdForUpdate() {
        TransactionValidator transactionValidator = getTransactionValidator();
        String id = "2";

        try {
            transactionValidator.validateIdForUpdate(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no transaction with id " + id, ipex.getMessage());
        }

        id = "1";

        try {
            transactionValidator.validateIdForUpdate(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateIdForAdd() {
        TransactionValidator transactionValidator = getTransactionValidator();
        String id = "1";

        try {
            transactionValidator.validateIdForAdd(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is a transaction with id " + id, ipex.getMessage());
        }

        id = "2";

        try {
            transactionValidator.validateIdForAdd(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateCarId() {
        TransactionValidator transactionValidator = getTransactionValidator();
        String id = "2";

        try {
            transactionValidator.validateCarId(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no car with id " + id, ipex.getMessage());
        }

        id = "1";

        try {
            transactionValidator.validateCarId(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateClientCardIdWhichCanBeNull() {
        TransactionValidator transactionValidator = getTransactionValidator();
        String clientCardId = "";

        try {
            transactionValidator.validateClientCardIdWhichCanBeNull(clientCardId);
        } catch (IdProblemException ipex) {
            fail();
        }

        clientCardId = "99";

        try {
            transactionValidator.validateClientCardIdWhichCanBeNull(clientCardId);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no client card with id " + clientCardId, ipex.getMessage());
        }
    }
}
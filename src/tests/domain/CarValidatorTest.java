package tests.domain;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.CarValidator;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.exceptions.InvalidValueException;
import com.ubb.postuniv.exceptions.StringFormatException;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarValidatorTest {

    CarValidator getCarValidator() {
        Repository<Car> carRepository = new InMemoryRepository<>();
        Car car = new Car("1", "yaris turbo", 2023, 0, true);
        carRepository.create(car);

        return new CarValidator(carRepository);
    }


    @Test
    void validateIdForUpdate() {
        CarValidator carValidator = getCarValidator();
        String id = "2";

        try {
            carValidator.validateIdForUpdate(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no car with id " + id, ipex.getMessage());
        }

        id = "1";

        try {
            carValidator.validateIdForUpdate(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateIdForAdd() {
        CarValidator carValidator = getCarValidator();
        String id = "1";

        try {
            carValidator.validateIdForAdd(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is a car with id " + id, ipex.getMessage());
        }

        id = "2";

        try {
            carValidator.validateIdForAdd(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @Test
    void validateModelFormat() {
        CarValidator carValidator = getCarValidator();
        String model = "";

        try {
            carValidator.validateModelFormat(model);
            fail();
        } catch (StringFormatException sfex) {
            assertEquals("Error: Model cannot be empty string.", sfex.getMessage());
        }

        model = "Model";

        try {
            carValidator.validateModelFormat(model);
        } catch (StringFormatException sfex) {
            fail();
        }
    }

    @Test
    void validateKm() {
        CarValidator carValidator = getCarValidator();
        int km = -1;

        try {
            carValidator.validateKm(km);
            fail();
        } catch (InvalidValueException ivex) {
            assertEquals("Error: Km should be greater or equal to 0.", ivex.getMessage());
        }

        km = 0;

        try {
            carValidator.validateKm(km);
        } catch (InvalidValueException ivex) {
            fail();
        }
    }

    @Test
    void validateYearOfPurchase() {
        CarValidator carValidator = getCarValidator();
        int year = -1;

        try {
            carValidator.validateYearOfPurchase(year);
            fail();
        } catch (InvalidValueException ivex) {
            assertEquals("Error: Year of purchase should be greater or equal to 0.", ivex.getMessage());
        }

        year = 0;

        try {
            carValidator.validateYearOfPurchase(year);
        } catch (InvalidValueException ivex) {
            fail();
        }
    }
}
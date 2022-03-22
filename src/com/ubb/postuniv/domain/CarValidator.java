package com.ubb.postuniv.domain;

import com.ubb.postuniv.exceptions.InvalidValueException;
import com.ubb.postuniv.exceptions.StringFormatException;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.repository.Repository;

public class CarValidator {
    private final Repository<Car> carRepository;

    public CarValidator(Repository<Car> carRepository) {
        this.carRepository = carRepository;
    }

    public void validateIdForUpdate(String id) throws IdProblemException {
        Car car = carRepository.read(id);
        if (car == null) {
            throw new IdProblemException("Error: There is no car with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws IdProblemException {
        Car car = carRepository.read(id);
        if (car != null) {
            throw new IdProblemException("Error: There already is a car with id " + id);
        }
    }

    public void validateModelFormat(String input) throws StringFormatException {
        if (input.isBlank()) {
            throw new StringFormatException("Error: Model cannot be empty string.");
        }
    }

    public void validateKm(int km) throws InvalidValueException {
        if (km < 0) {
            throw new InvalidValueException("Error: Km should be greater or equal to 0.");
        }
    }

    public void validateYearOfPurchase(int year) throws InvalidValueException {
        if (year < 0) {
            throw new InvalidValueException("Error: Year of purchase should be greater or equal to 0.");
        }
    }
}

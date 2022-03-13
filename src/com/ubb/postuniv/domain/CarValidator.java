package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.Repository;

public class CarValidator {
    private Repository<Car> carRepository;

    public CarValidator(Repository<Car> carRepository) {
        this.carRepository = carRepository;
    }

    public void validateIdForUpdate(String id) throws RuntimeException {
        Car car = carRepository.read(id);
        if (car == null) {
            throw new RuntimeException("Error: There is no car with id " + id);
        }
    }

    public void validateIdForAdd(String id) throws RuntimeException {
        Car car = carRepository.read(id);
        if (car != null) {
            throw new RuntimeException("Error: There already is a room with id " + id);
        }
    }

    public void validateModelFormat(String input) throws RuntimeException {
        if (input.equals("")) {
            throw new RuntimeException("Error: Model cannot be empty string.");
        }
    }

    public void validateKm(int km) {
        if (km < 0) {
            throw new RuntimeException("Error: Km should be greater or equal to 0.");
        }
    }

    public void validateYearOfPurchase(int year) {
        if (year < 0) {
            throw new RuntimeException("Error: Year of purchase should be greater or equal to 0.");
        }
    }
}

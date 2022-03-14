package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.Entity;
import com.ubb.postuniv.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private Repository<Car> carRepository;

    public CarService(Repository<Car> carRepository) {
        this.carRepository = carRepository;
    }

    //add
    public void add(String id, String model, int yearOfPurchase, int km, boolean warranty) throws RuntimeException {
        Car car = new Car(id, model, yearOfPurchase, km, warranty);
        carRepository.create(car);
    }

    //get all ordered by id
    public List<Car> getAll() {
        return carRepository.readAll()
                .stream()
                .sorted(Comparator.comparing(Car::getId))
                .collect(Collectors.toList());
    }

    //get a car by id
    public Car get(String id) {
        return carRepository.read(id);
    }

    //update
    public void update(String id, String model, int yearOfPurchase, int km, boolean warranty) throws RuntimeException {
        Car car = new Car(id, model, yearOfPurchase, km, warranty);
        carRepository.update(car);
    }

    //delete
    public void delete(String id) throws RuntimeException {
        carRepository.delete(id);
    }

    public List<Car> getAllCarsFullTextSearch(String searchTerm) {
        return carRepository.readAll()
                .stream()
                .filter(car -> car.getId().toLowerCase().contains(searchTerm) ||
                        car.getModel().toLowerCase().contains(searchTerm) ||
                        String.valueOf(car.getYearOfPurchase()).contains(searchTerm) ||
                        String.valueOf(car.getKm()).contains(searchTerm))
                .collect(Collectors.toList());
    }
}

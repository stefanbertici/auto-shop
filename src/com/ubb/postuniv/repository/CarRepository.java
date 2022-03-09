package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepository {
    private Map<String, Car> cars;

    public CarRepository() {
        this.cars = new HashMap<>();
    }

    //create
    public void create(Car car) throws RuntimeException {
        if (cars.containsKey(car.getId())) {
            throw new RuntimeException("Error: There already is a car with id " + car.getId());
        } else {
            cars.put(car.getId(), car);
        }
    }

    //read
    public List<Car> readAll() {
        return new ArrayList<>(cars.values());
    }

    public Car read(String id) {
        return cars.getOrDefault(id, null);
    }

    //update
    public void update(Car car) throws RuntimeException {
        if (cars.containsKey(car.getId())) {
            cars.put(car.getId(), car);
        } else {
            throw new RuntimeException("Error: There is no car with id " + car.getId());
        }
    }

    //delete
    public void delete(String id) throws RuntimeException {
        if (cars.containsKey(id)) {
            cars.remove(id);
        } else {
            throw new RuntimeException("Error: There is no car with id " + id);
        }
    }
}

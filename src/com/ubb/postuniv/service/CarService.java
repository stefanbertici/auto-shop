package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.repository.CarRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    //add
    public void add(String id, String model, int yearOfPurchase, int km, boolean warranty) throws RuntimeException{
        Car car = new Car(id, model, yearOfPurchase, km, warranty);
        carRepository.create(car);
    }

    //get all ordered by id
    public List<Car> getAll() {
        List<Car> carsById = new ArrayList<>(carRepository.readAll());
        carsById.sort(new Comparator<>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        return carsById;
    }

    //get a car by id
    public Car get(String id) {
        return carRepository.read(id);
    }

    //update
    public void update(String id, String model, int yearOfPurchase, int km, boolean warranty) throws RuntimeException{
        Car car = new Car(id, model, yearOfPurchase, km, warranty);
        carRepository.update(car);
    }

    //delete
    public void delete(String id) throws RuntimeException {
        carRepository.delete(id);
    }
}

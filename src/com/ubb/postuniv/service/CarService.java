package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.CarWithSumOfLaborPrice;
import com.ubb.postuniv.domain.Transaction;
import com.ubb.postuniv.repository.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CarService {
    private Repository<Car> carRepository;
    private Repository<Transaction> transactionRepository;

    public CarService(Repository<Car> carRepository, Repository<Transaction> transactionRepository) {
        this.carRepository = carRepository;
        this.transactionRepository = transactionRepository;
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

    public int updateAllCarWarranties() {
        LocalDate now = LocalDate.now();
        boolean outOfWarranty = false;
        AtomicInteger count = new AtomicInteger(0);

        carRepository.readAll()
                .stream()
                .filter(Car::isWarranty)
                .filter(car -> (car.getKm() >= 60000) || (now.getYear() - car.getYearOfPurchase() >= 3))
                .forEach(car -> {
                        carRepository.update(new Car(car.getId(), car.getModel(), car.getYearOfPurchase(), car.getKm(), false));
                        count.getAndIncrement();
                });

        return count.get();
    }

    //delete
    public void delete(String id) throws RuntimeException {
        carRepository.delete(id);
    }

    //reports
    public List<Car> getCarsFullTextSearch(String searchTerm) {
        return carRepository.readAll()
                .stream()
                .filter(car -> car.getId().toLowerCase().contains(searchTerm) ||
                        car.getModel().toLowerCase().contains(searchTerm) ||
                        String.valueOf(car.getYearOfPurchase()).contains(searchTerm) ||
                        String.valueOf(car.getKm()).contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<CarWithSumOfLaborPrice> getCarsOrderedDescendingBySumOfLaborPrice() {
        Map<String, Double> carIdAndLaborGroupings = transactionRepository.readAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCarId, Collectors.summingDouble(Transaction::getLaborPrice)));

        return carIdAndLaborGroupings.entrySet()
                .stream()
                .map(e -> new CarWithSumOfLaborPrice(carRepository.read(e.getKey()), e.getValue()))
                .sorted(Comparator.comparingDouble(CarWithSumOfLaborPrice::getTotalLaborPrice).reversed())
                .collect(Collectors.toList());
    }
}

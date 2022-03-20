package com.ubb.postuniv.domain;

public class CarWithSumOfLaborPrice {
    private final Car car;
    private final double sumOfLaborPrice;

    public CarWithSumOfLaborPrice(Car car, double sumOfLaborPrice) {
        this.car = car;
        this.sumOfLaborPrice = sumOfLaborPrice;
    }

    public Car getCar() {
        return car;
    }

    public double getTotalLaborPrice() {
        return sumOfLaborPrice;
    }

    @Override
    public String toString() {
        return "CarWithSumOfLaborPrice{" +
                "car=" + car +
                ", sumOfLaborPrice=" + sumOfLaborPrice +
                '}';
    }
}

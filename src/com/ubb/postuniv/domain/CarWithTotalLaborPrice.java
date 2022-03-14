package com.ubb.postuniv.domain;

public class CarWithTotalLaborPrice {
    private Car car;
    private double totalLaborPrice;

    public CarWithTotalLaborPrice(Car car, double totalLaborPrice) {
        this.car = car;
        this.totalLaborPrice = totalLaborPrice;
    }

    public Car getCar() {
        return car;
    }

    public double getTotalLaborPrice() {
        return totalLaborPrice;
    }

    @Override
    public String toString() {
        return "CarWithTotalLaborPrice{" +
                "car=" + car +
                ", totalLaborPrice=" + totalLaborPrice +
                '}';
    }
}

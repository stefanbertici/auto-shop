package com.ubb.postuniv.domain;

public class Car extends Entity{
    private final String model;
    private final int yearOfPurchase;
    private final int km;
    private final boolean warranty;

    public Car(String id, String model, int yearOfPurchase, int km, boolean warranty) {
        super(id);
        this.model = model;
        this.yearOfPurchase = yearOfPurchase;
        this.km = km;
        this.warranty = warranty;
    }

    public String getModel() {
        return model;
    }

    public int getYearOfPurchase() {
        return yearOfPurchase;
    }

    public int getKm() {
        return km;
    }

    public boolean isWarranty() {
        return warranty;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", yearOfPurchase=" + yearOfPurchase +
                ", km=" + km +
                ", warranty=" + warranty +
                '}';
    }
}
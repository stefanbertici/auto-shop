package com.ubb.postuniv.domain;

public class Car {
    // id, model, an achiziție, nr. km, în garanție. Km și anul achiziției să fie strict pozitivi.
    private String id;
    private String model;
    private int yearOfPurchase;
    private int km;
    private boolean warranty;

    public Car(String id, String model, int yearOfPurchase, int km, boolean warranty) {
        this.id = id;
        this.model = model;
        this.yearOfPurchase = yearOfPurchase;
        this.km = km;
        this.warranty = warranty;
    }

    public String getId() {
        return id;
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
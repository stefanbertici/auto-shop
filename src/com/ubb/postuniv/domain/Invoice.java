package com.ubb.postuniv.domain;

public class Invoice {
    private final double partPrice;
    private final double laborPrice;
    private final double partDiscount;
    private final double laborDiscount;
    private final double finalPartPrice;
    private final double finalLaborPrice;

    public Invoice(double partPrice, double laborPrice, double partDiscount, double laborDiscount, double finalPartPrice, double finalLaborPrice) {
        this.partPrice = partPrice;
        this.laborPrice = laborPrice;
        this.partDiscount = partDiscount;
        this.laborDiscount = laborDiscount;
        this.finalPartPrice = finalPartPrice;
        this.finalLaborPrice = finalLaborPrice;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public double getLaborPrice() {
        return laborPrice;
    }

    public double getPartDiscount() {
        return partDiscount;
    }

    public double getLaborDiscount() {
        return laborDiscount;
    }

    public double getFinalPartPrice() {
        return finalPartPrice;
    }

    public double getFinalLaborPrice() {
        return finalLaborPrice;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "partPrice=" + partPrice +
                ", laborPrice=" + laborPrice +
                ", partDiscount=" + partDiscount +
                ", laborDiscount=" + laborDiscount +
                ", finalPartPrice=" + finalPartPrice +
                ", finalLaborPrice=" + finalLaborPrice +
                '}';
    }
}

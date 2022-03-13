package com.ubb.postuniv.domain;

import java.time.LocalDateTime;

public class Transaction extends Entity{
    private String carId;
    private String clientCardId;
    private double partPrice;
    private double laborPrice;
    private LocalDateTime dateAndTime;

    public Transaction(String id, String carId,String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime) {
        super(id);
        this.carId = carId;
        this.clientCardId = clientCardId;
        this.partPrice = partPrice;
        this.laborPrice = laborPrice;
        this.dateAndTime = dateAndTime;
    }

    public String getCarId() {
        return carId;
    }

    public String getClientCardId() {
        return clientCardId;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public double getLaborPrice() {
        return laborPrice;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", clientCardId='" + clientCardId + '\'' +
                ", partPrice=" + partPrice +
                ", laborPrice=" + laborPrice +
                ", dateAndTime=" + dateAndTime +
                '}';
    }
}

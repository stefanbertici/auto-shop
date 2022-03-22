package com.ubb.postuniv.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Transaction extends Entity{
    private final String carId;
    private final String clientCardId;
    private final double partPrice;
    private final double laborPrice;
    private final LocalDateTime dateAndTime;
    private final double appliedDiscount;

    public Transaction(String id, String carId,String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime, double appliedDiscount) {
        super(id);
        this.carId = carId;
        this.clientCardId = clientCardId;
        this.partPrice = partPrice;
        this.laborPrice = laborPrice;
        this.dateAndTime = dateAndTime;
        this.appliedDiscount = appliedDiscount;
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

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
        return "Transaction{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", clientCardId='" + clientCardId + '\'' +
                ", partPrice=" + partPrice +
                ", laborPrice=" + laborPrice +
                ", dateAndTime='" + dateAndTime.format(dateTimeFormatter) + '\'' +
                ", appliedDiscount=" + appliedDiscount +
                '}';
    }
}

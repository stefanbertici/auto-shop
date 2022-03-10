package com.ubb.postuniv.domain;

import java.time.LocalDateTime;

public class Transaction {
    // id, id_mașină, id_card_client (poate fi nul), sumă piese, sumă manoperă, data și ora (`dd.mm.yyyy HH:mm`).
    // Dacă există un card client, atunci aplicați o reducere de `10%` pentru manoperă. Dacă mașina este în garanție, atunci piesele sunt gratis.
    // Se tipărește prețul plătit și reducerile acordate.
    private String id;
    private String carId;
    private String clientCardId;
    private double partPrice;
    private double laborPrice;
    private LocalDateTime dateAndTime;

    public Transaction(String id, String carId,String clientCardId, double partPrice, double laborPrice, LocalDateTime dateAndTime) {
        this.id = id;
        this.carId = carId;
        this.clientCardId = clientCardId;
        this.partPrice = partPrice;
        this.laborPrice = laborPrice;
        this.dateAndTime = dateAndTime;
    }

    public String getId() {
        return id;
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

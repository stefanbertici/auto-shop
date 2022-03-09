package com.ubb.postuniv.domain;

public class Transaction {
    // id, id_mașină, id_card_client (poate fi nul), sumă piese, sumă manoperă, data și ora (`dd.mm.yyyy HH:mm`).
    // Dacă există un card client, atunci aplicați o reducere de `10%` pentru manoperă. Dacă mașina este în garanție, atunci piesele sunt gratis.
    // Se tipărește prețul plătit și reducerile acordate.
    private String id;
    private String clientCardId;
    private double partPrice;
    private double laborPrice;
    private String dateAndTime;

    public Transaction(String id, String clientCardId, double partPrice, double laborPrice, String dateAndTime) {
        this.id = id;
        this.clientCardId = clientCardId;
        this.partPrice = partPrice;
        this.laborPrice = laborPrice;
        this.dateAndTime = dateAndTime;
    }

    public String getId() {
        return id;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", clientCardId='" + clientCardId + '\'' +
                ", partPrice=" + partPrice +
                ", laborPrice=" + laborPrice +
                ", dateAndTime='" + dateAndTime + '\'' +
                '}';
    }
}

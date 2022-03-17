package com.ubb.postuniv.domain;

public class ClientCardWithSumOfDiscounts {
    private ClientCard clientCard;
    private double sumOfDiscounts;

    public ClientCardWithSumOfDiscounts(ClientCard clientCard, double sumOfDiscounts) {
        this.clientCard = clientCard;
        this.sumOfDiscounts = sumOfDiscounts;
    }

    public ClientCard getClientCard() {
        return clientCard;
    }

    public double getSumOfDiscounts() {
        return sumOfDiscounts;
    }

    @Override
    public String toString() {
        return "ClientCardWithSumOfDiscounts{" +
                "clientCard=" + clientCard +
                ", sumOfDiscounts=" + sumOfDiscounts +
                '}';
    }
}

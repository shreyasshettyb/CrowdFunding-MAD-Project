package com.example.crowdfunding.Models;

public class CollectorOverview {
    private final String collectorName;
    private final double amount;

    public CollectorOverview(String collectorName, Double amount) {
        this.collectorName = collectorName;
        this.amount = amount;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public Double getAmount() {
        return amount;
    }
}

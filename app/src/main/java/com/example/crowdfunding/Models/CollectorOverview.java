package com.example.crowdfunding.Models;

public class CollectorOverview {
    private final String collectorName, collectorEmail;
    private final double amount;

    public CollectorOverview(String collectorName, String collectorEmail, Double amount) {
        this.collectorName = collectorName;
        this.collectorEmail = collectorEmail;
        this.amount = amount;
    }

    public String getCollectorName() {
        return collectorName;
    }
    public String getCollectorEmail() {
        return collectorEmail;
    }

    public Double getAmount() {
        return amount;
    }
}

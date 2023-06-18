package com.example.crowdfunding.Models;

public class Transaction {
    private final String donorName, upiID, dateTime, collectorEmail, fundingCode, collectorName;
    private final  double amount;

    public Transaction(String donorName, String upiID, String dateTime, double amount, String collectorEmail, String fundingCode, String collectorName) {
        this.donorName = donorName;
        this.upiID = upiID;
        this.dateTime = dateTime;
        this.amount = amount;
        this.collectorEmail = collectorEmail;
        this.fundingCode = fundingCode;
        this.collectorName = collectorName;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getUpiID() {
        return upiID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getCollectorEmail() {
        return collectorEmail;
    }

    public String getFundingCode() { return fundingCode; }

    public String getCollectorName() { return collectorName; }

}

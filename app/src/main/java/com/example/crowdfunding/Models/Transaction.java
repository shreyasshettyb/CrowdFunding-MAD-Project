package com.example.crowdfunding.Models;

public class Transaction {
    private final String donorName, upiID, transactionID, dateTime, collectorEmail, fundingCode, collectorName;
    private final  double amount;

    public Transaction(String donorName, String upiID, String transactionID, String dateTime, double amount, String volunteerEmail, String fundingCode, String collecotorName) {
        this.donorName = donorName;
        this.upiID = upiID;
        this.transactionID = transactionID;
        this.dateTime = dateTime;
        this.amount = amount;
        this.collectorEmail = volunteerEmail;
        this.fundingCode = fundingCode;
        this.collectorName = collecotorName;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getUpiID() {
        return upiID;
    }

    public String getTransactionID() {
        return transactionID;
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

package com.example.crowdfunding;

public class Transaction {
    private String donorName, upiID, transactionID, dateTime, volunteeremail;
    private double amount;

    public Transaction(String donorName, String upiID, String transactionID, String dateTime, double amount, String volunteeremail) {
        this.donorName = donorName;
        this.upiID = upiID;
        this.transactionID = transactionID;
        this.dateTime = dateTime;
        this.amount = amount;
        this.volunteeremail = volunteeremail;
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

    public String getVolunteeremail() {
        return volunteeremail;
    }
}

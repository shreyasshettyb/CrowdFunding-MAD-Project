package com.example.crowdfunding.Models;

public class DonorOverview {
    private final String donorName, upiId, donationDate;
    private final double amountDonated;

    public DonorOverview(String donorName, String upiId, String donationDate, double amountDonated) {
        this.donorName = donorName;
        this.upiId = upiId;
        this.donationDate = donationDate;
        this.amountDonated = amountDonated;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getUpiId() {
        return upiId;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public double getAmountDonated() {
        return amountDonated;
    }
}

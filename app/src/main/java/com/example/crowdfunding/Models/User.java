package com.example.crowdfunding.Models;

public class User {
    private final String name, email, password, type, fundingCode, fundingName, upiAddress;

    public User(String name, String email, String password, String type, String fundingCode, String fundingName, String upiAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.fundingCode = fundingCode;
        this.fundingName = fundingName;
        this.upiAddress = upiAddress;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getFundingCode() {
        return fundingCode;
    }
    public String getFundingName() {
        return fundingName;
    }

    public String getUpiAddress() { return upiAddress; }
}

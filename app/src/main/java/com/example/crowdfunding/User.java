package com.example.crowdfunding;

public class User {
    private String name, email, password, type, fundingCode;

    public User(String name, String email, String password, String type, String fundingCode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.fundingCode = fundingCode;
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
}

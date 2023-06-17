package com.example.crowdfunding;

public class User {
    private String name, email, password, type, fundingCode, fundingName;

    public User(String name, String email, String password, String type, String fundingCode, String fundingName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.fundingCode = fundingCode;
        this.fundingName = fundingName;
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
}

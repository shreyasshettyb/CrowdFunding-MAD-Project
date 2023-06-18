package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CollectFunds extends AppCompatActivity {
    String username, fundingCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_money);

        Bundle bundle = getIntent().getBundleExtra("data");
        username = bundle.getString("Username");


    }
}
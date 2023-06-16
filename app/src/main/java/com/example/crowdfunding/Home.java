package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getBundleExtra("data");
        username = bundle.getString("Username");
        String text = bundle.getString("Text");
        TextView t = findViewById(R.id.fundingCodeHome);
        t.setText(text);
    }
}
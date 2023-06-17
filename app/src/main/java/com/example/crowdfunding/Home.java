package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getBundleExtra("data");
        username = bundle.getString("Username");
        try {
            String[] text = bundle.getString("Text").split("\\+");
            TextView t1 = findViewById(R.id.fundingNameHome);
            t1.setText(text[0]);
            TextView t2 = findViewById(R.id.fundingCodeHome);
            t2.setText(text[1]);
        }catch(Exception e){
            Log.e("mytag", "" + e);
        }
    }

    public void getFundDetails(View v){
        try {
            Intent intent = new Intent(Home.this, TotalCollection.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }catch(Exception e){
            Log.e("mytag", "" + e);
        }
    }

    public void collectFunds(View v){
        try {
            Intent intent = new Intent(Home.this, CollectFunds.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }catch(Exception e){
            Log.e("mytag", "" + e);
        }
    }
}
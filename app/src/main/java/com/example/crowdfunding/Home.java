package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    String username, fundingCode, fundingName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            username = bundle.getString("Username");
            fundingCode = bundle.getString("FundingCode");
            fundingName = bundle.getString("FundingName");
            String text = bundle.getString("Text");
            TextView t1 = findViewById(R.id.fundingNameHome);
            t1.setText(fundingName);
            TextView t2 = findViewById(R.id.fundingCodeHome);
            t2.setText(text);
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void getFundDetails(View v){
        try {
            Bundle bundle = new Bundle();
            bundle.putString("FundingCode", fundingCode);
            Intent intent = new Intent(Home.this, TotalCollection.class);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void collectFunds(View v){
        try {
            Bundle bundle = new Bundle();
            bundle.putString("Username", username);
            bundle.putString("FundingCode", fundingCode);
            bundle.putString("FundingName", fundingName);
            Intent intent = new Intent(Home.this, CollectFunds.class);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }
}
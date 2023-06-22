package com.example.crowdfunding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    String username, fundingCode, fundingName;
    private long backPressedTime;
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
            bundle.putString("Username", username);
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

    public void logout(View v){
        Intent intent = new Intent(Home.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
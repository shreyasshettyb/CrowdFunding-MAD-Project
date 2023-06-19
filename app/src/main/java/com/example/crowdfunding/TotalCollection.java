package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.crowdfunding.Models.CollectorOverview;

public class TotalCollection extends AppCompatActivity {
    RecyclerView recyclerView;
    TransactionDBHelper transactionDBHelper;
    TextView msgTextView;
    String fundingCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_collection);
        msgTextView = findViewById(R.id.msgTextView_totalCollection);
        try {
            recyclerView = findViewById(R.id.recyclerViewTotalCollection);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            transactionDBHelper = new TransactionDBHelper(this, "transactionDB", null, 1);

            Bundle bundle = getIntent().getBundleExtra("data");
            fundingCode = bundle.getString("FundingCode");

            CollectorOverview[] transactionList = transactionDBHelper.getCollectorOverview(fundingCode);

            if(transactionList.length == 0)
                msgTextView.setText(R.string.no_funds_collected_yet);
            else if (transactionList[0].getCollectorName().equals("Error")) {
                msgTextView.setText(R.string.error_occurred_please_try_again);
            }
            else {
                TotalCollectionAdapter adapter = new TotalCollectionAdapter(transactionList);
                recyclerView.setAdapter(adapter);
            }
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void viewDetailsHandler(View v){

        String collectorEmail = ((TextView) findViewById(R.id.collectorEmail_totalCollection)).getText().toString();
        String collectorName = ((TextView) findViewById(R.id.collectorName_totalCollection)).getText().toString();
        String totalAmount = ((TextView) findViewById(R.id.amount_totalCollection)).getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("CollectorEmail", collectorEmail);
        bundle.putString("CollectorName", collectorName);
        bundle.putString("TotalAmount", totalAmount);

        Intent intent = new Intent(TotalCollection.this, FundDetails.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}
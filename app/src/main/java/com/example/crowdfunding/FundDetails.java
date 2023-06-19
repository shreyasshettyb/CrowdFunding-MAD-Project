package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdfunding.Models.DonorOverview;

public class FundDetails extends AppCompatActivity {
    String collectorName, collectorEmail, totalAmount;
    TransactionDBHelper transactionDBHelper;
    RecyclerView recyclerView;
    TextView dateTime, amountASC, amountDESC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_specific);

        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            collectorName = bundle.getString("CollectorName");
            collectorEmail = bundle.getString("CollectorEmail");
            totalAmount = bundle.getString("TotalAmount");

            ((TextView)findViewById(R.id.collectorEmail_collectorSpecific)).setText(collectorEmail);
            ((TextView)findViewById(R.id.collectorName_collectorSpecific)).setText(collectorName);
            ((TextView)findViewById(R.id.totalAmount_collectorSpecific)).setText(totalAmount);

            dateTime = findViewById(R.id.dateCollected_collectorSpecific);
            amountASC = findViewById(R.id.amountAsc_collectorSpecific);
            amountDESC = findViewById(R.id.amountDesc_collectorSpecific);

            recyclerView = findViewById(R.id.recyclerViewCollectorSpecific);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            transactionDBHelper = new TransactionDBHelper(this, "transactionDB", null, 1);

            DonorOverview[] transactionList = transactionDBHelper.getCollectorSpecific(collectorEmail, 1, 0, 1);

            if(transactionList.length == 0 || transactionList[0].getDonorName().equals("Error"))
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            else {
                 CollectorSpecificAdapter adapter = new CollectorSpecificAdapter(transactionList);
                recyclerView.setAdapter(adapter);
            }
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void sortBy(View v){
        int dateT = 0, amtASC = 0, amtDESC = 0;
        int green = 0xFF43A045;
        int black = 0xFF000000;

        try{
            String txt = ((TextView) v).getText().toString();
            if(txt.equals("DateCollected")) {
                dateT = 1;
                dateTime.setTextColor(green);
                amountASC.setTextColor(black);
                amountDESC.setTextColor(black);
            }
            else if(txt.equals("Amount ASC")) {
                amtASC = 1;
                dateTime.setTextColor(black);
                amountASC.setTextColor(green);
                amountDESC.setTextColor(black);
            }
            else {
                amtDESC = 1;
                dateTime.setTextColor(black);
                amountASC.setTextColor(black);
                amountDESC.setTextColor(green);
            }

            DonorOverview[] transactionList = transactionDBHelper.getCollectorSpecific(collectorEmail, dateT, amtASC, amtDESC);

            if(transactionList.length == 0 || transactionList[0].getDonorName().equals("Error"))
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            else {
                CollectorSpecificAdapter adapter = new CollectorSpecificAdapter(transactionList);
                recyclerView.setAdapter(adapter);
            }
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }
}
package com.example.crowdfunding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdfunding.Models.CollectorOverview;

public class TotalCollection extends AppCompatActivity {
    RecyclerView recyclerView;
    TransactionDBHelper transactionDBHelper;
    UserDBHelper userDBHelper;
    TextView msgTextView;
    String username, fundingCode, type;
    Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_collection);
        msgTextView = findViewById(R.id.msgTextView_totalCollection);
        try {
            downloadBtn = findViewById(R.id.download_totalCollection);
            recyclerView = findViewById(R.id.recyclerViewTotalCollection);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            userDBHelper = new UserDBHelper(this, "userDB", null, 1);
            transactionDBHelper = new TransactionDBHelper(this, "transactionDB", null, 1);

            Bundle bundle = getIntent().getBundleExtra("data");
            username = bundle.getString("Username");
            fundingCode = bundle.getString("FundingCode");
            type = userDBHelper.getUserType(username);
            if(type.equals("Error")){
                Toast.makeText(this, "Something went wrong, Please Log In again", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TotalCollection.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            String txtButton;
            if(type.equalsIgnoreCase("Admin"))
                txtButton = "GET ENTIRE DATA";
            else
                txtButton = "GET YOUR DATA";
            downloadBtn.setText(txtButton);

            CollectorOverview[] transactionList = transactionDBHelper.getCollectorOverview(fundingCode);

            if(transactionList.length == 0) {
                msgTextView.setText(R.string.no_funds_collected_yet);
                downloadBtn.setVisibility(View.GONE);
            }
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

        downloadBtn.setOnClickListener(v -> {

            if(transactionDBHelper.exportTransactionsToCSV(username, fundingCode, type)){
                Toast.makeText(TotalCollection.this, "Download Successful", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(TotalCollection.this, "Download Unsuccessful, Please try again",
                        Toast.LENGTH_LONG).show();
            }
        });
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
package com.example.crowdfunding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdfunding.Models.CollectorOverview;

public class TotalCollection extends AppCompatActivity {
    RecyclerView recyclerView;
    TransactionDBHelper transactionDBHelper;
    TextView msgTextView;
    String fundingCode;
    Button downloadBtn;

    private static final int REQUEST_WRITE_STORAGE = 112;
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

        downloadBtn = findViewById(R.id.download_totalCollection);
        downloadBtn.setOnClickListener(v -> {
            if (hasWritePermission()) {
                if(transactionDBHelper.exportTransactionsToCSV(fundingCode)){
                    Toast.makeText(TotalCollection.this, "Download Successful", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(TotalCollection.this, "Download Unsuccessful, Please try again", Toast.LENGTH_LONG).show();
                }
            } else {
                requestWritePermission();
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

    private boolean hasWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
    }


    private void requestWritePermission() {
        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                transactionDBHelper.exportTransactionsToCSV(fundingCode);
            } else {
                Toast.makeText(this, "Write permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.crowdfunding.Adapters.TotalCollectionAdapter;
import com.example.crowdfunding.DBHelpers.TransactionDBHelper;

public class TotalCollection extends AppCompatActivity {
    RecyclerView recyclerView;
    TransactionDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_collection);
        Intent it = getIntent();

        recyclerView = findViewById(R.id.recyclerViewTotalCollection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        helper = new TransactionDBHelper(this, "innodb", null, 1);
        TotalCollectionAdapter adapter = new TotalCollectionAdapter(helper.getCollectorOverview(it.getStringExtra("FundingCode")));
        recyclerView.setAdapter(adapter);
    }
}
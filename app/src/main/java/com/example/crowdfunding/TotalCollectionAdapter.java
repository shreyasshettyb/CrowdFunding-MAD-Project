package com.example.crowdfunding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdfunding.Models.CollectorOverview;

public class TotalCollectionAdapter extends RecyclerView.Adapter<TotalCollectionAdapter.ViewHolder> {

    private final CollectorOverview[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView collectorName, amount, collectorEmail;

        public ViewHolder(View view) {
            super(view);

            collectorName = view.findViewById(R.id.collectorName_totalCollection);
            collectorEmail = view.findViewById(R.id.collectorEmail_totalCollection);
            amount = view.findViewById(R.id.amount_totalCollection);
        }

        public TextView getCollectorNameTextView() {
            return collectorName;
        }
        public TextView getCollectorEmailTextView() {
            return collectorEmail;
        }
        public TextView getAmountTextView() {
            return amount;
        }

    }

    public TotalCollectionAdapter(CollectorOverview[] dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item_total_collection, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.getCollectorNameTextView().setText(localDataSet[position].getCollectorName());
        viewHolder.getCollectorEmailTextView().setText(localDataSet[position].getCollectorEmail());
        viewHolder.getAmountTextView().setText(String.format("%s", localDataSet[position].getAmount()));

        viewHolder.itemView.findViewById(R.id.viewDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectorEmail = localDataSet[position].getCollectorEmail();
                String collectorName = localDataSet[position].getCollectorName();
                String totalAmount = "" + localDataSet[position].getAmount();

                Bundle bundle = new Bundle();
                bundle.putString("CollectorEmail", collectorEmail);
                bundle.putString("CollectorName", collectorName);
                bundle.putString("TotalAmount", totalAmount);

                Intent intent = new Intent(v.getContext(), FundDetails.class);
                intent.putExtra("data", bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}

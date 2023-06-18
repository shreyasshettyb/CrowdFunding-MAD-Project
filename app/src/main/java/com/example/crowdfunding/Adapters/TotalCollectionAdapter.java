package com.example.crowdfunding.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdfunding.Models.CollectorOverview;
import com.example.crowdfunding.R;

public class TotalCollectionAdapter extends RecyclerView.Adapter<TotalCollectionAdapter.ViewHolder> {

    private final CollectorOverview[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView collectorName, amount;

        public ViewHolder(View view) {
            super(view);

            collectorName = (TextView) view.findViewById(R.id.collectorName_totalCollection);
            amount = (TextView) view.findViewById(R.id.amount_totalCollection);
        }

        public TextView getCollectorNameTextView() {
            return collectorName;
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getCollectorNameTextView().setText(localDataSet[position].getCollectorName());
        viewHolder.getAmountTextView().setText(String.format("%s", localDataSet[position].getAmount()));
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}


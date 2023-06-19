package com.example.crowdfunding;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdfunding.Models.DonorOverview;

public class CollectorSpecificAdapter extends RecyclerView.Adapter<CollectorSpecificAdapter.ViewHolder> {

    private final DonorOverview[] localDataSet;

    public CollectorSpecificAdapter(DonorOverview[] localDataSet) {
        this.localDataSet = localDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView donorName, upiId, donatedDate, donatedAmount, typeOfPayment;

        public ViewHolder(View view) {
            super(view);

            donorName = view.findViewById(R.id.donorName_collectorSpecific);
            upiId = view.findViewById(R.id.upiId_collectorSpecific);
            donatedDate = view.findViewById(R.id.date_collectorSpecific);
            donatedAmount = view.findViewById(R.id.amount_collectorSpecific);
            typeOfPayment = view.findViewById(R.id.type_collectorSpecific);
        }

        public TextView getDonorName() {
            return donorName;
        }
        public TextView getUpiId() {
            return upiId;
        }
        public TextView getDonatedDate() {
            return donatedDate;
        }

        public TextView getDonatedAmount() { return donatedAmount; }

        public TextView getTypeOfPayment() { return typeOfPayment; }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item_collector_specific, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String dName = localDataSet[position].getDonorName();
        if(dName.equals(""))
            dName = "Anonymous";
        viewHolder.getDonorName().setText(dName);

        String upi = localDataSet[position].getUpiId();
        String type = "UPI";
        if(upi.equals(""))
            type = "CASH";
        viewHolder.getTypeOfPayment().setText(type);

        viewHolder.getUpiId().setText(upi);

        viewHolder.getDonatedDate().setText(localDataSet[position].getDonationDate());
        viewHolder.getDonatedAmount().setText(String.format("%s", localDataSet[position].getAmountDonated()));
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}


package com.rumanweb.bidsell_ap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.models.BidPlaced;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BidPlacedAdapter extends RecyclerView.Adapter<BidPlacedAdapter.BidPlacedViewHolder> {
    private List<BidPlaced> bidPlacedList;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);

    public BidPlacedAdapter(List<BidPlaced> bidPlacedList) {
        this.bidPlacedList = bidPlacedList;
    }

    @NonNull
    @Override
    public BidPlacedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_bid_placed, parent, false);
        return new BidPlacedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidPlacedViewHolder holder, int position) {
        BidPlaced bidPlaced = bidPlacedList.get(position);

        holder.tvAuctionTitle.setText(bidPlaced.getAuctionTitle());
        holder.tvUserEmail.setText(bidPlaced.getUserEmail());
        holder.tvBiddingAmount.setText(currencyFormat.format(bidPlaced.getBidAmount()));
        holder.tvBiddingTime.setText(dateFormat.format(bidPlaced.getBiddingTime()));
        holder.tvListingNo.setText(String.valueOf(bidPlaced.getListingNo()));
    }

    @Override
    public int getItemCount() {
        return bidPlacedList.size();
    }

    static class BidPlacedViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuctionTitle, tvBiddingAmount, tvBiddingTime, tvListingNo, tvUserEmail;

        public BidPlacedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuctionTitle = itemView.findViewById(R.id.tvAuctionTitle);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvBiddingAmount = itemView.findViewById(R.id.tvBiddingAmount);
            tvBiddingTime = itemView.findViewById(R.id.tvBiddingTime);
            tvListingNo = itemView.findViewById(R.id.tvListingNo);
        }
    }
}
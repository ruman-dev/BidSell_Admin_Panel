package com.rumanweb.bidsell_ap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.models.Auctions;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AuctionsAdapter extends RecyclerView.Adapter<AuctionsAdapter.AuctionViewHolder> {
    private List<Auctions> auctionList;
    private SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public AuctionsAdapter(List<Auctions> auctionList) {
        this.auctionList = auctionList;
    }

    @NonNull
    @Override
    public AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_auctions, parent, false);
        return new AuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionViewHolder holder, int position) {
        Auctions auction = auctionList.get(position);

        holder.tvAuctionName.setText(auction.getAuctionName());
        holder.tvStartingPrice.setText(currencyFormat.format(auction.getStartingPrice()));

        try {
            Date startTime = inputFormat.parse(auction.getStartTime());
            Date endTime = inputFormat.parse(auction.getEndTime());

            holder.tvStartTime.setText(outputFormat.format(startTime));
            holder.tvEndTime.setText(outputFormat.format(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.tvStartTime.setText(auction.getStartTime());
            holder.tvEndTime.setText(auction.getEndTime());
        }
    }

    @Override
    public int getItemCount() {
        return auctionList.size();
    }

    static class AuctionViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuctionName, tvStartingPrice, tvStartTime, tvEndTime;

        public AuctionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuctionName = itemView.findViewById(R.id.tvAuctionName);
            tvStartingPrice = itemView.findViewById(R.id.tvStartingPrice);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
        }
    }
}
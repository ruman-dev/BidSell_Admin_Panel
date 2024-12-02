package com.rumanweb.bidsell_ap.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.models.AuctionRequest;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AuctionRequestAdapter extends RecyclerView.Adapter<AuctionRequestAdapter.RequestViewHolder> {
    private List<AuctionRequest> requestList;
    private SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm a", Locale.US);
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public AuctionRequestAdapter(List<AuctionRequest> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        AuctionRequest request = requestList.get(position);

        holder.tvRequestName.setText(request.getAuctionTitle());
        holder.tvRequestedPrice.setText(currencyFormat.format(request.getStartingPriceReq()));
        holder.tvStatus.setText(request.getStatus());
        holder.tvRequestDate.setText(outputFormat.format(request.getOpeningDate()));
        holder.tvExpectedEndDate.setText(outputFormat.format(request.getExpectedEndDate()));

        holder.itemView.setOnClickListener(v -> showDetailsDialog(v.getContext(), request));
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    private void showDetailsDialog(Context context, AuctionRequest request) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_auction_request);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Initialize dialog views
        TextView tvAuctionName = dialog.findViewById(R.id.tvAuctionNameValue);
        TextView tvOpeningTime = dialog.findViewById(R.id.tvOpeningTimeValue);
        TextView tvClosingTime = dialog.findViewById(R.id.tvClosingTimeValue);
        TextView tvDescription = dialog.findViewById(R.id.tvDescriptionValue);
        TextView tvHighlights = dialog.findViewById(R.id.tvHighlightsValue);
        TextView tvStartingPrice = dialog.findViewById(R.id.tvStartingPriceValue);
        TextView tvQuantity = dialog.findViewById(R.id.tvQuantityValue);
        TextView tvUserEmail = dialog.findViewById(R.id.tvUserEmailValue);
        Button btnApprove = dialog.findViewById(R.id.btnApprove);
        Button btnReject = dialog.findViewById(R.id.btnReject);

        // Set values
        tvAuctionName.setText(request.getAuctionTitle());
        tvOpeningTime.setText(request.getOpeningDate().toString());
        tvClosingTime.setText(request.getExpectedEndDate().toString());
        tvDescription.setText(request.getDescription());
        tvHighlights.setText(request.getHighlights());
        tvStartingPrice.setText(String.valueOf(request.getStartingPriceReq()));
        tvQuantity.setText(String.valueOf(request.getQuantity()));
        tvUserEmail.setText(request.getUserEmail());

        // Handle approve button click
        btnApprove.setOnClickListener(v -> {
//            updateAuctionStatus(context, request.getDocumentId(), "Approved");
            Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // Handle reject button click
        btnReject.setOnClickListener(v -> {
//            updateAuctionStatus(context, request.getDocumentId(), "Rejected");
            Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestName, tvRequestedPrice, tvRequestDate, tvExpectedEndDate, tvStatus;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestName = itemView.findViewById(R.id.tvAuctionName);
            tvRequestedPrice = itemView.findViewById(R.id.tvStartingPrice);
            tvRequestDate = itemView.findViewById(R.id.tvStartTime);
            tvExpectedEndDate = itemView.findViewById(R.id.tvEndTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
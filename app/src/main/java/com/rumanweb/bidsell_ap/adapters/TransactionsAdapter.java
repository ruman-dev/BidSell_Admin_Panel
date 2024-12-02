package com.rumanweb.bidsell_ap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.models.Transaction;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
    private List<Transaction> transactionList;
    private SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);
    private NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.US);

    public TransactionsAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        // Set user information
        holder.textFullName.setText(transaction.getFullName());
        holder.textUserName.setText(transaction.getUserName());
        holder.textEmail.setText(transaction.getEmail());

        // Set transaction amount with formatting
        holder.textAmount.setText(currencyFormat.format(transaction.getAmount()));
        holder.textPaymentMethod.setText("(" + transaction.getPaymentMethod() + ")");

        // Set transaction ID
        holder.textPayment.setText(transaction.getTransactionId());

        // Set formatted transaction time
        Date transactionTime = transaction.getTransactionTime();
        holder.textTime.setText(outputFormat.format(transactionTime));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView textFullName, textUserName, textEmail, textAmount, textPayment, textTime, textPaymentMethod;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textFullName = itemView.findViewById(R.id.textFullName);
            textUserName = itemView.findViewById(R.id.textUserName);
            textEmail = itemView.findViewById(R.id.textTrEmail);
            textAmount = itemView.findViewById(R.id.textTrAmount);
            textPayment = itemView.findViewById(R.id.textTrPayment);
            textTime = itemView.findViewById(R.id.textTrTime);
            textPaymentMethod = itemView.findViewById(R.id.textPaymentMethod);
        }
    }
}
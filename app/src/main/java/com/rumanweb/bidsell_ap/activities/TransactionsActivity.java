package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.TransactionsAdapter;
import com.rumanweb.bidsell_ap.models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    RecyclerView recyclerViewTransactions;
    private TransactionsAdapter transactionsAdapter;
    private List<Transaction> transactionList;
    private List<Transaction> filteredTransactionList;
    TextInputEditText edTransaction;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarTrans);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerViewTransactions = findViewById(R.id.transRecyclerView);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));

        // Populate Transaction List (You'll replace this with actual data from your database)
        transactionList = new ArrayList<>();
        filteredTransactionList = new ArrayList<>();

        // Setup Adapter
        transactionsAdapter = new TransactionsAdapter(filteredTransactionList);
        recyclerViewTransactions.setAdapter(transactionsAdapter);

        retrieveTransactions();
        // Search EditText
        edTransaction = findViewById(R.id.edSearchTrans);
        edTransaction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTransactions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Filter transactions based on user name, email, or transaction ID
    private void filterTransactions(String searchText) {
        filteredTransactionList.clear();

        if (searchText.isEmpty()) {
            filteredTransactionList.addAll(transactionList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (Transaction transaction : transactionList) {
                if (transaction.getFullName().toLowerCase().contains(searchTextLower) ||
                        transaction.getUserName().toLowerCase().contains(searchTextLower) ||
                        transaction.getEmail().toLowerCase().contains(searchTextLower) ||
                        transaction.getTransactionId().toLowerCase().contains(searchTextLower)) {
                    filteredTransactionList.add(transaction);
                }
            }
        }
        transactionsAdapter.notifyDataSetChanged();
    }

    private void retrieveTransactions() {
        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    String userId = documentSnapshot.getId();
                    db.collection("users")
                            .document(userId)
                            .collection("payments")
                            .get()
                            .addOnCompleteListener(paymentTask -> {
                                if (paymentTask.isSuccessful()) {
                                    for (DocumentSnapshot paymentDoc : paymentTask.getResult()) {
                                        double paidAmount = paymentDoc.getDouble("paidAmount");
                                        String paymentMethod = paymentDoc.getString("paymentMethod");
                                        String transactionId = paymentDoc.getString("transactionId");
                                        Date transactionTime = paymentDoc.getDate("transactionTime");
                                        String userEmail = paymentDoc.getString("userEmail");
                                        String userFullName = paymentDoc.getString("userFullName");
                                        String userName = paymentDoc.getString("userName");

                                        Transaction transaction = new Transaction(
                                                userFullName,
                                                userName,
                                                userEmail,
                                                Double.parseDouble(String.valueOf(paidAmount)),
                                                paymentMethod,
                                                transactionId,
                                                transactionTime
                                        );
                                        transactionList.add(transaction);
                                    }
                                    filteredTransactionList.clear();
                                    filteredTransactionList.addAll(transactionList);
                                    transactionsAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e("FirestoreError", "Error fetching payments", paymentTask.getException());
                                }
                            });
                }
            } else {
                Log.e("FirestoreError", "Error fetching users", task.getException());
            }
        });
    }
}
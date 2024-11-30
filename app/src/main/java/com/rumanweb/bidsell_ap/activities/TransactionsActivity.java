package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.TransactionsAdapter;
import com.rumanweb.bidsell_ap.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransactions;
    private TransactionsAdapter transactionsAdapter;
    private List<Transaction> transactionList;
    private List<Transaction> filteredTransactionList;
    private TextInputEditText edTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarTrans);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize RecyclerView
        recyclerViewTransactions = findViewById(R.id.transRecyclerView);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));

        // Populate Transaction List (You'll replace this with actual data from your database)
        transactionList = generateSampleTransactions();
        filteredTransactionList = new ArrayList<>(transactionList);

        // Setup Adapter
        transactionsAdapter = new TransactionsAdapter(filteredTransactionList);
        recyclerViewTransactions.setAdapter(transactionsAdapter);

        // Search EditText
        edTransaction = findViewById(R.id.edSearchTrans);
        edTransaction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTransactions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
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

    // Sample method to generate transactions - replace with actual data retrieval
    private List<Transaction> generateSampleTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(
                "John Doe",
                "johndoe123",
                "john.doe@example.com",
                25000.0,
                "bKash",
                "A5DS2GTR9",
                "2024-03-24 13:20:00"
        ));
        transactions.add(new Transaction(
                "Jane Smith",
                "janesmith456",
                "jane.smith@example.com",
                18500.0,
                "bKash",
                "B7HJ4KLP2",
                "2024-03-23 15:45:00"
        ));
        transactions.add(new Transaction(
                "Mike Johnson",
                "mikej",
                "mike.johnson@example.com",
                32000.0,
                "bKash",
                "C9MN6QRS4",
                "2024-03-22 09:30:00"
        ));
        return transactions;

    }
}
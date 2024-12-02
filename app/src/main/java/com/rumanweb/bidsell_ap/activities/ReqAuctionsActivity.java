package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.AuctionRequestAdapter;
import com.rumanweb.bidsell_ap.models.AuctionRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReqAuctionsActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private RecyclerView recyclerViewRequests;
    private AuctionRequestAdapter requestAdapter;
    private List<AuctionRequest> requestList;
    private List<AuctionRequest> filteredRequestList;
    private TextInputEditText edRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_auctions);

        MaterialToolbar toolbar = findViewById(R.id.toolbarReq);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerViewRequests = findViewById(R.id.requestRecyclerView);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(this));

        // Initialize lists and adapter
        requestList = new ArrayList<>();
        filteredRequestList = new ArrayList<>();
        requestAdapter = new AuctionRequestAdapter(filteredRequestList);
        recyclerViewRequests.setAdapter(requestAdapter);

        retrieveAuctionRequest();

        // Setup search functionality
        edRequests = findViewById(R.id.edSearchReq);
        edRequests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRequests(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterRequests(String searchText) {
        filteredRequestList.clear();

        if (searchText.isEmpty()) {
            filteredRequestList.addAll(requestList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (AuctionRequest request : requestList) {
                if (request.getAuctionTitle().toLowerCase().contains(searchTextLower) ||
                        request.getUserEmail().toLowerCase().contains(searchTextLower)) {
                    filteredRequestList.add(request);
                }
            }
        }

        requestAdapter.notifyDataSetChanged();
    }

    private void retrieveAuctionRequest() {
        db.collection("CreateAuctions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            String auctionName = document.getString("auctionName");
                            Date closingTime = document.getDate("closingTime");
                            String description = document.getString("description");
                            String highlights = document.getString("highlights");
                            String imageLink = document.getString("imageLink");
                            Date openingTime = document.getDate("openingTime");
                            long quantity = document.getLong("quantity");
                            double startingPrice = document.getDouble("startingPrice");
                            String status = document.getString("status");
                            String userEmail = document.getString("userEmail");

                            AuctionRequest request = new AuctionRequest(auctionName, startingPrice, openingTime, closingTime, status, description, highlights,
                                    imageLink, quantity, userEmail);
                            requestList.add(request);
                        }

                        filteredRequestList.addAll(requestList);
                        requestAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error fetching document!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
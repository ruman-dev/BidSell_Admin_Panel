package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.BidPlacedAdapter;
import com.rumanweb.bidsell_ap.adapters.TransactionsAdapter;
import com.rumanweb.bidsell_ap.models.BidPlaced;
import com.rumanweb.bidsell_ap.models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BidPlacedActivity extends AppCompatActivity {

    RecyclerView recyclerViewBidPlaced;
    TextInputEditText edBidPlaced;
    FirebaseFirestore db;
    private BidPlacedAdapter bidPlacedAdapter;
    private List<BidPlaced> bidPlacedList;
    private List<BidPlaced> filteredBidPlacedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_placed);

        MaterialToolbar toolbar = findViewById(R.id.toolbarBidPlaced);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();

        recyclerViewBidPlaced = findViewById(R.id.placeBidRecyclerView);
        recyclerViewBidPlaced.setLayoutManager(new LinearLayoutManager(this));

        // Populate Transaction List (You'll replace this with actual data from your database)
        bidPlacedList = new ArrayList<>();
        filteredBidPlacedList = new ArrayList<>();

        // Setup Adapter
        bidPlacedAdapter = new BidPlacedAdapter(filteredBidPlacedList);
        recyclerViewBidPlaced.setAdapter(bidPlacedAdapter);

        retrieveBiddingList();
        // Search EditText
        edBidPlaced = findViewById(R.id.edSearchPlaceBid);
        edBidPlaced.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredBidPlaced(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void filteredBidPlaced(String searchText) {
        filteredBidPlacedList.clear();

        if (searchText.isEmpty()) {
            filteredBidPlacedList.addAll(bidPlacedList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (BidPlaced bidPlaced : bidPlacedList) {
                if (bidPlaced.getAuctionTitle().toLowerCase().contains(searchTextLower) ||
                        bidPlaced.getUserEmail().toLowerCase().contains(searchTextLower) ||
                        String.valueOf(bidPlaced.getListingNo()).toLowerCase().contains(searchTextLower)) {
                    filteredBidPlacedList.add(bidPlaced);
                }
            }
        }
        bidPlacedAdapter.notifyDataSetChanged();
    }

    private void retrieveBiddingList() {
        db.collectionGroup("User")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bidPlacedList.clear();

                        for (QueryDocumentSnapshot userDoc : task.getResult()) {
                            try {
                                String auctionTitle = userDoc.getString("productTitle");
                                String bidderEmail = userDoc.getString("bidderName");
                                Double bidAmount = userDoc.getDouble("biddingPrice");
                                Date biddingTime = userDoc.getDate("biddingTime");
                                Long listingNo = userDoc.getLong("productListingNo");

                                if (auctionTitle != null && bidderEmail != null &&
                                        bidAmount != null && biddingTime != null && listingNo != null) {

                                    BidPlaced biddingPlaced = new BidPlaced(
                                            auctionTitle,
                                            bidderEmail,
                                            listingNo,
                                            bidAmount,
                                            biddingTime
                                    );

                                    bidPlacedList.add(biddingPlaced);
                                }
                            } catch (Exception e) {
                                Log.e("BidPlacedActivity", "Error processing bid document", e);
                            }
                        }

                        // Update filtered list and adapter
                        filteredBidPlacedList.clear();
                        filteredBidPlacedList.addAll(bidPlacedList);
                        bidPlacedAdapter.notifyDataSetChanged();

                    } else {
                        Log.e("FirestoreError", "Error fetching User documents", task.getException());
                    }
                });
    }

}
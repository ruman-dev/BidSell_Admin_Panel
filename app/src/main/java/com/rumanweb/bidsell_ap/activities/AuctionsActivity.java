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
import com.rumanweb.bidsell_ap.adapters.AuctionsAdapter;
import com.rumanweb.bidsell_ap.models.Auctions;

import java.util.ArrayList;
import java.util.List;

public class AuctionsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAuctions;
    private AuctionsAdapter auctionsAdapter;
    private List<Auctions> auctionList;
    private List<Auctions> filteredAuctionList;
    private TextInputEditText edAuctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarAuctions);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize RecyclerView
        recyclerViewAuctions = findViewById(R.id.auctionsRecyclerView);
        recyclerViewAuctions.setLayoutManager(new LinearLayoutManager(this));

        // Populate Auction List (You'll replace this with actual data from your database)
        auctionList = generateSampleAuctions();
        filteredAuctionList = new ArrayList<>(auctionList);

        // Setup Adapter
        auctionsAdapter = new AuctionsAdapter(filteredAuctionList);
        recyclerViewAuctions.setAdapter(auctionsAdapter);

        // Search EditText
        edAuctions = findViewById(R.id.edSearchAuctions);
        edAuctions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAuctions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Filter auctions based on auction name or description
    private void filterAuctions(String searchText) {
        filteredAuctionList.clear();

        if (searchText.isEmpty()) {
            filteredAuctionList.addAll(auctionList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (Auctions auction : auctionList) {
                // Check if auction name contains search text
                if (auction.getAuctionName().toLowerCase().contains(searchTextLower)) {
                    filteredAuctionList.add(auction);
                }
            }
        }

        auctionsAdapter.notifyDataSetChanged();
    }

    // Sample method to generate auctions - replace with actual data retrieval
    private List<Auctions> generateSampleAuctions() {
        List<Auctions> auctions = new ArrayList<>();
        auctions.add(new Auctions(
                "Maryland Grand Estate Decor Auction",
                45000,
                "2024-10-24 10:25:00",
                "2024-12-24 21:41:00"
        ));
        auctions.add(new Auctions(
                "Vintage Jewelry Collectors Auction",
                65000,
                "2024-11-15 14:30:00",
                "2025-01-20 18:00:00"
        ));
        auctions.add(new Auctions(
                "Classic Car Auction Extravaganza",
                120000,
                "2024-09-05 09:00:00",
                "2024-11-10 16:45:00"
        ));

        auctions.add(new Auctions(
                "Classic Car Auction Extravaganza",
                120000,
                "2024-09-05 09:00:00",
                "2024-11-10 16:45:00"
        ));
        auctions.add(new Auctions(
                "Classic Car Auction Extravaganza",
                120000,
                "2024-09-05 09:00:00",
                "2024-11-10 16:45:00"
        ));
        return auctions;
    }
}
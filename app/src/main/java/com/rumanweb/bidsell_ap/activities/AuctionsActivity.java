package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.AuctionsAdapter;
import com.rumanweb.bidsell_ap.models.Auctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AuctionsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAuctions;
    private AuctionsAdapter auctionsAdapter;
    private List<Auctions> auctionList;
    private List<Auctions> filteredAuctionList;
    private TextInputEditText edAuctions;
    private FirebaseFirestore db;
    private SimpleDateFormat displayDateFormat;

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

        db = FirebaseFirestore.getInstance();

        auctionList = new ArrayList<>();
        filteredAuctionList = new ArrayList<>();

        // Setup Adapter
        auctionsAdapter = new AuctionsAdapter(filteredAuctionList);
        recyclerViewAuctions.setAdapter(auctionsAdapter);

        retrieveAuctions();

        // Adjust the date format to match your needs
        displayDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());

        // Search EditText
        edAuctions = findViewById(R.id.edSearchAuctions);
        edAuctions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAuctions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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
                if (auction.getTitle().toLowerCase().contains(searchTextLower)) {
                    filteredAuctionList.add(auction);
                }
            }
        }
        auctionsAdapter.notifyDataSetChanged();
    }

    private void retrieveAuctions() {
        db.collection("ProductList").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                auctionList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title = document.getString("title");
                    String description = document.getString("description");
                    String highlights = document.getString("highlights");
                    String imgUrl = document.getString("img_url");
                    long listingNo = document.getLong("listing_no");
                    long bidCount = document.getLong("bid_count");
                    Date openDate = document.getDate("open_date");
                    Date closeDate = document.getDate("close_date");
                    long quantity = document.getLong("quantity");
                    double startingPrice = document.getDouble("starting_price");

                    String formattedOpenDate = displayDateFormat.format(openDate);
                    String formattedCloseDate = displayDateFormat.format(closeDate);


                    Auctions auctions = new Auctions(
                            title, description, highlights, imgUrl, bidCount, listingNo, quantity, startingPrice, formattedOpenDate, formattedCloseDate);
                    auctionList.add(auctions);
                }
                filteredAuctionList.addAll(auctionList);
                auctionsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Error fetching auctions!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.rumanweb.bidsell_ap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.AuctionRequestAdapter;
import com.rumanweb.bidsell_ap.models.AuctionRequest;

import java.util.ArrayList;
import java.util.List;

public class ReqAuctionsActivity extends AppCompatActivity {

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

        // Initialize RecyclerView
        recyclerViewRequests = findViewById(R.id.requestRecyclerView);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(this));

        // Initialize lists and adapter
        requestList = generateSampleRequests();
        filteredRequestList = new ArrayList<>(requestList);
        requestAdapter = new AuctionRequestAdapter(filteredRequestList);
        recyclerViewRequests.setAdapter(requestAdapter);

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
                if (request.getRequestName().toLowerCase().contains(searchTextLower)) {
                    filteredRequestList.add(request);
                }
            }
        }

        requestAdapter.notifyDataSetChanged();
    }

    private List<AuctionRequest> generateSampleRequests() {
        List<AuctionRequest> requests = new ArrayList<>();

        requests.add(new AuctionRequest(
                "req001",
                "Antique Furniture Collection",
                2500,
                "2024-05-15 18:00:00",
                "2024-06-04 10:30:00",
                "Pending",
                "Elegant antique furniture for collectors.",
                "Includes rare 19th-century pieces.",
                "https://picsum.photos/200/300",
                "100",
                "collector1@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req002",
                "Vintage Car Collection",
                50000,
                "2024-06-01 12:00:00",
                "2024-07-01 15:00:00",
                "Pending",
                "A set of classic vintage cars.",
                "Features iconic models from the 1950s.",
                "https://picsum.photos/200/301",
                "200",
                "collector2@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req003",
                "Rare Coin Collection",
                10000,
                "2024-07-10 10:00:00",
                "2024-08-10 20:00:00",
                "Pending",
                "Collection of rare and ancient coins.",
                "Includes coins from various civilizations.",
                "https://picsum.photos/200/302",
                "50",
                "collector3@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req004",
                "Art Masterpieces",
                7500,
                "2024-09-05 08:00:00",
                "2024-10-05 18:00:00",
                "Pending",
                "Original art pieces by renowned artists.",
                "Perfect for art enthusiasts and collectors.",
                "https://picsum.photos/200/303",
                "120",
                "collector4@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req005",
                "Luxury Watch Collection",
                15000,
                "2024-03-01 14:00:00",
                "2024-03-30 19:00:00",
                "Pending",
                "Exclusive collection of luxury watches.",
                "Includes limited-edition timepieces.",
                "https://picsum.photos/200/304",
                "75",
                "collector5@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req006",
                "Modern Jewelry Set",
                12000,
                "2024-08-20 16:00:00",
                "2024-09-20 14:00:00",
                "Pending",
                "Stylish and modern jewelry designs.",
                "Includes diamond and gold sets.",
                "https://picsum.photos/200/305",
                "90",
                "collector6@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req007",
                "Historical Manuscripts",
                20000,
                "2024-04-15 10:00:00",
                "2024-05-15 17:00:00",
                "Pending",
                "Collection of rare historical manuscripts.",
                "Great for history buffs and researchers.",
                "https://picsum.photos/200/306",
                "110",
                "collector7@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req008",
                "Antique Weaponry",
                18000,
                "2024-02-01 09:00:00",
                "2024-02-28 18:00:00",
                "Pending",
                "Rare collection of antique swords and weapons.",
                "Perfect for museums and collectors.",
                "https://picsum.photos/200/307",
                "60",
                "collector8@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req009",
                "Classic Books Collection",
                5000,
                "2024-11-01 10:00:00",
                "2024-12-01 20:00:00",
                "Pending",
                "First editions of classic literature.",
                "Great for bibliophiles and scholars.",
                "https://picsum.photos/200/308",
                "85",
                "collector9@domain.com"
        ));

        requests.add(new AuctionRequest(
                "req010",
                "Rare Stamps Collection",
                7000,
                "2024-10-10 15:00:00",
                "2024-11-10 12:00:00",
                "Pending",
                "A collection of rare stamps from around the world.",
                "Includes limited-edition and vintage stamps.",
                "https://picsum.photos/200/309",
                "45",
                "collector10@domain.com"
        ));

        return requests;
    }

}
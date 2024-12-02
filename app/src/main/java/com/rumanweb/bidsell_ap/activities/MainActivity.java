package com.rumanweb.bidsell_ap.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumanweb.bidsell_ap.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView mainNavView;
    FrameLayout mainFrameLayout;
    RelativeLayout layoutActiveUsers, layoutActiveAuctions, layoutBidPlaced, layoutTransactions, layoutReqForAuctions;
    FirebaseFirestore firestore;
    TextView tvTotalActiveUsers, tvTotalAuctions, tvTotalBidPlaced, tvTotalTransactions, tvTotalAuctionRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        mainNavView = findViewById(R.id.mainNavView);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        layoutActiveUsers = findViewById(R.id.layoutActiveUsers);
        layoutActiveAuctions = findViewById(R.id.layoutActiveAuctions);
        layoutBidPlaced = findViewById(R.id.layoutBidPlaced);
        layoutTransactions = findViewById(R.id.layoutTransactions);
        layoutReqForAuctions = findViewById(R.id.layoutReqForAuctions);

        tvTotalActiveUsers = findViewById(R.id.tvTotalActiveUsers);
        tvTotalAuctions = findViewById(R.id.tvTotalAuctions);
        tvTotalBidPlaced = findViewById(R.id.tvTotalBidPlaced);
        tvTotalTransactions = findViewById(R.id.tvTotalTransactions);
        tvTotalAuctionRequests = findViewById(R.id.tvTotalAuctionRequests);

        layoutActiveUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UsersActivity.class));
            }
        });

        layoutActiveAuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AuctionsActivity.class));
            }
        });

        layoutBidPlaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BidPlacedActivity.class));
            }
        });

        layoutTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TransactionsActivity.class));
            }
        });

        layoutReqForAuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReqAuctionsActivity.class));
            }
        });
        navDrawerFunc();

        realTimeAuctionRequests();
        realTimeDataToHomeScreen();
        retrieveBidPlacedCountRealTime();

    }

    private void realTimeAuctionRequests() {
        firestore.collection("CreateAuctions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("FirestoreError", e.getMessage());
                    return;
                }
                if (snapshots != null) {
                    int userCount = snapshots.size();
                    tvTotalAuctionRequests.setText(String.valueOf(userCount));
                }
            }
        });

    }

    private void realTimeDataToHomeScreen() {
        firestore.collection("users").addSnapshotListener((usersSnapshot, userError) -> {
            if (userError != null) {
                Log.e("FirestoreError", "Error fetching users in real-time", userError);
                return;
            }
            if (usersSnapshot != null && !usersSnapshot.isEmpty()) {
                final AtomicInteger totalTransactionCount = new AtomicInteger(0);

                for (DocumentSnapshot userDoc : usersSnapshot.getDocuments()) {
                    String userId = userDoc.getId();

                    firestore.collection("users")
                            .document(userId)
                            .collection("payments")
                            .addSnapshotListener((paymentsSnapshot, paymentsError) -> {
                                if (paymentsError != null) {
                                    Log.e("FirestoreError", "Error fetching payments for user: " + userId, paymentsError);
                                    return;
                                }

                                if (paymentsSnapshot != null) {
                                    int currentTransactionCount = paymentsSnapshot.size();

                                    // Update the total transaction count
                                    totalTransactionCount.addAndGet(currentTransactionCount);

                                    // Update the transaction count on the UI
                                    runOnUiThread(() -> {
                                        tvTotalTransactions.setText(String.valueOf(totalTransactionCount.get()));
                                    });
                                }
                            });

                }
            }
        });
    }

    private void retrieveBidPlacedCountRealTime() {

        firestore.collection("biddingList").addSnapshotListener((biddingListSnapshot, biddingListError) -> {
            if (biddingListError != null) {
                Log.e("FirestoreError", "Error fetching biddingList in real-time", biddingListError);
                return;
            }

            if (biddingListSnapshot != null && !biddingListSnapshot.isEmpty()) {
                final AtomicInteger totalBidPlacedCount = new AtomicInteger(0); // To store total bid placed count

                // Iterate over all "biddingList" documents
                for (DocumentSnapshot biddingListDoc : biddingListSnapshot.getDocuments()) {
                    String biddingListId = biddingListDoc.getId();

                    // Listen to the "User" sub-collection for each "biddingList"
                    firestore.collection("biddingList")
                            .document(biddingListId)
                            .collection("User")
                            .addSnapshotListener((userSnapshot, userError) -> {
                                if (userError != null) {
                                    Log.e("FirestoreError", "Error fetching Users for biddingList: " + biddingListId, userError);
                                    return;
                                }

                                if (userSnapshot != null) {
                                    int currentBidCount = userSnapshot.size();

                                    // Update the total bid placed count
                                    totalBidPlacedCount.addAndGet(currentBidCount);

                                    // Update the bid count on the UI
                                    runOnUiThread(() -> {
                                        tvTotalBidPlaced.setText(String.valueOf(totalBidPlacedCount.get()));
                                    });
                                }
                            });
                }
            }
        });
    }


    private void navDrawerFunc() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar, R.string.drawer_close, R.string.drawer_open);

        drawerLayout.addDrawerListener(toggle);
        mainNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.menu_dashboard) {
                    Toast.makeText(MainActivity.this, "Dashboard Selected", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.menu_users) {
                    Toast.makeText(MainActivity.this, "Users Selected", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.menu_auctions) {
                    Toast.makeText(MainActivity.this, "Auctions Selected", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }
}
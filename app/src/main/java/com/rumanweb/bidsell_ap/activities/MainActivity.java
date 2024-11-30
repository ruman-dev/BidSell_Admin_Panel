package com.rumanweb.bidsell_ap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.rumanweb.bidsell_ap.R;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView mainNavView;
    FrameLayout mainFrameLayout;
    RelativeLayout layoutActiveUsers, layoutActiveAuctions, layoutBidPlaced, layoutTransactions, layoutReqForAuctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        mainNavView = findViewById(R.id.mainNavView);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        layoutActiveUsers = findViewById(R.id.layoutActiveUsers);
        layoutActiveAuctions = findViewById(R.id.layoutActiveAuctions);
        layoutBidPlaced = findViewById(R.id.layoutBidPlaced);
        layoutTransactions = findViewById(R.id.layoutTransactions);
        layoutReqForAuctions = findViewById(R.id.layoutReqForAuctions);

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

    }
    private void navDrawerFunc() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar, R.string.drawer_close, R.string.drawer_open);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
package com.rumanweb.bidsell_ap.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumanweb.bidsell_ap.R;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText edUserId, edPassword;
    String strUserId, strPassword;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        btnSignIn = findViewById(R.id.btnSignIn);
        edUserId = findViewById(R.id.edUserId);
        edPassword = findViewById(R.id.edPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserId = edUserId.getText().toString().trim();
                strPassword = edPassword.getText().toString().trim();

                if (!strUserId.isEmpty() && !strPassword.isEmpty()) {
                    // Check for internet connection
                    if (hasInternet(LoginActivity.this)) {
                        // Query Firestore to find the user
                        db.collection("admin_users")
                                .whereEqualTo("userId", strUserId)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult() != null) {
                                            if (task.getResult().isEmpty()) {
                                                Toast.makeText(LoginActivity.this, "No user found!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    String passwordFromDb = document.getString("password");
                                                    if (passwordFromDb != null && passwordFromDb.equals(strPassword)) {
                                                        // Password matches, proceed to main activity
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Error getting user data!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(LoginActivity.this, "Internet Connection Lost! Please try again later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter both user ID and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    Boolean hasInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);

        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
    }
}
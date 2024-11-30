package com.rumanweb.bidsell_ap.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.adapters.UsersAdapter;
import com.rumanweb.bidsell_ap.models.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;
    private List<Users> userList;
    private List<Users> filteredUserList;
    private TextInputEditText searchEditText;

    // Firestore instance
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarUsers);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize RecyclerView
        recyclerViewUsers = findViewById(R.id.usersRecyclerView);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize lists
        userList = new ArrayList<>();
        filteredUserList = new ArrayList<>();

        // Setup Adapter
        usersAdapter = new UsersAdapter(filteredUserList);
        recyclerViewUsers.setAdapter(usersAdapter);

        // Retrieve data from Firestore
        fetchUsersFromFirestore();

        // Search EditText
        searchEditText = findViewById(R.id.edSearchUsers);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Fetch users from Firestore
    private void fetchUsersFromFirestore() {
        firestore.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String fullName = document.getString("fullName");
                    String userName = document.getString("userName");
                    String email = document.getString("email");
                    String mobile = document.getString("mobile");

                    Users user = new Users(fullName, userName, email, mobile);
                    userList.add(user);
                }
                filteredUserList.addAll(userList);
                usersAdapter.notifyDataSetChanged();
            } else {
                Log.e("FirestoreError", "Error fetching users", task.getException());
            }
        });
    }

    // Filter users based on username or full name
    private void filterUsers(String searchText) {
        filteredUserList.clear();

        if (searchText.isEmpty()) {
            filteredUserList.addAll(userList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (Users user : userList) {
                if (user.getUserName().toLowerCase().contains(searchTextLower) ||
                        user.getFullName().toLowerCase().contains(searchTextLower)) {
                    filteredUserList.add(user);
                }
            }
        }

        usersAdapter.notifyDataSetChanged();
    }
}

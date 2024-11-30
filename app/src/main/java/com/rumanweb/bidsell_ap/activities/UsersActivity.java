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

        // Populate User List (You'll replace this with actual data from your database)
        userList = generateSampleUsers();
        filteredUserList = new ArrayList<>(userList);

        // Setup Adapter
        usersAdapter = new UsersAdapter(filteredUserList);
        recyclerViewUsers.setAdapter(usersAdapter);

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

    // Filter users based on username or full name
    private void filterUsers(String searchText) {
        filteredUserList.clear();

        if (searchText.isEmpty()) {
            filteredUserList.addAll(userList);
        } else {
            String searchTextLower = searchText.toLowerCase();
            for (Users user : userList) {
                // Check if username or full name contains search text
                if (user.getUserName().toLowerCase().contains(searchTextLower) ||
                        user.getFullName().toLowerCase().contains(searchTextLower)) {
                    filteredUserList.add(user);
                }
            }
        }

        usersAdapter.notifyDataSetChanged();
    }

    // Sample method to generate users - replace with actual data retrieval
    private List<Users> generateSampleUsers() {
        List<Users> users = new ArrayList<>();
        users.add(new Users("John Doe", "johndoe123", "john.doe@example.com", "+1 (555) 123-4567"));
        users.add(new Users("Jane Smith", "janesmith456", "jane.smith@example.com", "+1 (555) 987-6543", "Premium User"));
        users.add(new Users("Mike Johnson", "mikej", "mike.johnson@example.com", "+1 (555) 246-8135", "New User"));
        users.add(new Users("Alice Williams", "alicew", "alice.williams@example.com", "+1 (555) 369-2580"));
        users.add(new Users("Md. Ruman", "rumancse", "ruman.cse@example.com", "+8801994-385596"));
        users.add(new Users("Alduddin Al Hasan", "hasanal", "hasan.alauddin@example.com", "01700533952", "New User"));
        return users;
    }
}
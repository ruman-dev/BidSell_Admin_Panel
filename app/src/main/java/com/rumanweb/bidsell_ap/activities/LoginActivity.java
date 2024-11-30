package com.rumanweb.bidsell_ap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rumanweb.bidsell_ap.R;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText edUserId, edPassword;
    String strUserId, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btnSignIn);
        edUserId = findViewById(R.id.edUserId);
        edPassword = findViewById(R.id.edPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserId = edUserId.getText().toString();
                strPassword = edPassword.getText().toString();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
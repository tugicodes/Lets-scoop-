package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference databaseRef;
    Button LoginBtn;
    TextView SignUpBtn;
    TextInputEditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mEmail = findViewById(R.id.textEmail);
        mPassword = findViewById(R.id.textPassword);
        SignUpBtn = findViewById(R.id.tvSignUp);
        LoginBtn = findViewById(R.id.loginbtn);

        fAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        SignUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginPage.this, SignUpPage.class));
        });

        LoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("Password must be ≥6 characters");
                return;
            }

            // Firebase Auth
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = fAuth.getCurrentUser().getUid();

                            // Update last login timestamp
                            HashMap<String, Object> updateMap = new HashMap<>();
                            updateMap.put("lastLogin", System.currentTimeMillis());

                            databaseRef.child(userId).updateChildren(updateMap)
                                    .addOnSuccessListener(aVoid -> {
                                        startActivity(new Intent(LoginPage.this, Dashboard.class));
                                        finish();
                                    });
                        } else {
                            Toast.makeText(LoginPage.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
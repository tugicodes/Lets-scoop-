package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class SignUpPage extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        TextInputEditText emailEditText = findViewById(R.id.textEmail);
        TextInputEditText passwordEditText = findViewById(R.id.textPassword);
        TextInputEditText fullNameEditText = findViewById(R.id.fname);
        TextInputEditText usernameEditText = findViewById(R.id.textUsername);
        Button signUpBtn = findViewById(R.id.btnsignup);
        TextView logInBtn = findViewById(R.id.tvLogin);

        fAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        // Redirect if already logged in
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }

        signUpBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required.");
                return;
            }
            if (password.length() < 6) {
                passwordEditText.setError("Password must be ≥6 characters");
                return;
            }
            if (TextUtils.isEmpty(fullName)) {
                fullNameEditText.setError("Full name is required.");
                return;
            }
            if (TextUtils.isEmpty(username)) {
                usernameEditText.setError("Username is required.");
                return;
            }

            // Check if email exists (modern API)
            fAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> signInMethods = task.getResult().getSignInMethods();
                            boolean isNewUser = signInMethods == null || signInMethods.isEmpty();

                            if (isNewUser) {
                                fAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(registerTask -> {
                                            if (registerTask.isSuccessful()) {
                                                String userId = fAuth.getCurrentUser().getUid();
                                                HashMap<String, Object> userMap = new HashMap<>();
                                                userMap.put("userId", userId);
                                                userMap.put("email", email);
                                                userMap.put("fullName", fullName);
                                                userMap.put("username", username);
                                                userMap.put("timestamp", System.currentTimeMillis());

                                                databaseRef.child(userId).setValue(userMap)
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(SignUpPage.this,
                                                                    "Account created!",
                                                                    Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                                            finish();
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            String errorMsg = e.getMessage() != null ? e.getMessage() : "Unknown error";
                                                            Toast.makeText(SignUpPage.this,
                                                                    "Error saving data: " + errorMsg,
                                                                    Toast.LENGTH_SHORT).show();
                                                        });
                                            } else {
                                                String errorMsg = registerTask.getException() != null ?
                                                        registerTask.getException().getMessage() : "Unknown error";
                                                Toast.makeText(SignUpPage.this,
                                                        "Error: " + errorMsg,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(SignUpPage.this,
                                        "Email already exists. Please log in instead.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            String errorMsg = task.getException() != null ?
                                    task.getException().getMessage() : "Unknown error";
                            Toast.makeText(SignUpPage.this,
                                    "Error checking email: " + errorMsg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        logInBtn.setOnClickListener(v -> {
            startActivity(new Intent(SignUpPage.this, LoginPage.class));
            finish();
        });
    }
}
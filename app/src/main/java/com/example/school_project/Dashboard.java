package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Spinner;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dashboard extends baseActivity {

    private MaterialCardView iceCreamCard, cakeCard, crepesCard;
    private TextView welcomeText;

    // Firebase
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        fAuth = FirebaseAuth.getInstance();

        iceCreamCard = findViewById(R.id.icecreamcardView);
        cakeCard = findViewById(R.id.cakecardView);
        crepesCard = findViewById(R.id.crepescardView);
        welcomeText = findViewById(R.id.our_services);

        if (fAuth.getCurrentUser() != null) {
            welcomeText.setText("Welcome, " + fAuth.getCurrentUser().getEmail() + "!\nSweet treats:");
        }

        iceCreamCard.setOnClickListener(v -> showDessertDetail("Ice Cream"));
        cakeCard.setOnClickListener(v -> showDessertDetail("Cake"));
        crepesCard.setOnClickListener(v -> showDessertDetail("Crepes"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner cakeSpinner = findViewById(R.id.cakeSpinner);

// Cake list data
        List<Map<String, String>> cakeList = new ArrayList<>();

        Map<String, String> c1 = new HashMap<>();
        c1.put("title", "Black Forest");
        c1.put("description", "Rich chocolate cake with cherries");
        cakeList.add(c1);

        Map<String, String> c2 = new HashMap<>();
        c2.put("title", "Red Velvet");
        c2.put("description", "Creamy and smooth");
        cakeList.add(c2);

        Map<String, String> c3 = new HashMap<>();
        c3.put("title", "Cheesecake");
        c3.put("description", "Soft and cheesy");
        cakeList.add(c3);

        Map<String, String> c4 = new HashMap<>();
        c4.put("title", "Passion Fruit Cake");
        c4.put("description", "Tropical and moist");
        cakeList.add(c4);

        Map<String, String> c5 = new HashMap<>();
        c5.put("title", "Moist Chocolate Fudge Cake");
        c5.put("description", "Deep, rich chocolate goodness");
        cakeList.add(c5);

// Adapter
        String[] from = {"title", "description"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(this, cakeList, android.R.layout.simple_list_item_2, from, to);
        cakeSpinner.setAdapter(adapter);


        cakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> selected = (Map<String, String>) parent.getItemAtPosition(position);
                Toast.makeText(Dashboard.this, "Selected: " + selected.get("title"), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void showDessertDetail(String dessertName) {
        Intent intent;
        switch (dessertName) {
            case "Ice Cream":
                intent = new Intent(this, Icecreams.class);
                break;
            case "Cake":
                intent = new Intent(this, Cakes.class);
                break;
            case "Crepes":
                intent = new Intent(this, Crepes.class);
                break;
            default:
                Toast.makeText(this, "Selection not available", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);

    }

    public void logout(View view) {
        fAuth.signOut();
        startActivity(new Intent(this, LoginPage.class));
        finish();
    }
}
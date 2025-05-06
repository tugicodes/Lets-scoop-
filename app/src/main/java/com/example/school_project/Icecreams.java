package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;

public class Icecreams extends baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_icecreams);

        MaterialCardView vanillaCard = findViewById(R.id.vanillaicecardView);
        MaterialCardView strawberryCard = findViewById(R.id.strawberryicecardView);
        MaterialCardView chocolateCard = findViewById(R.id.chocolateicecardView);

        vanillaCard.setOnClickListener(v -> {
            Intent intent = new Intent(Icecreams.this, VanillaIcecream.class);
            intent.putExtra("flavor", "Vanilla");
            intent.putExtra("color", "#D9A86C"); // Card background color
            startActivity(intent);
        });

        strawberryCard.setOnClickListener(v -> {
            Intent intent = new Intent(Icecreams.this, StrawberryIcecream.class);
            intent.putExtra("flavor", "Strawberry");
            intent.putExtra("color", "#D38B90");
            startActivity(intent);
        });

        chocolateCard.setOnClickListener(v -> {
            Intent intent = new Intent(Icecreams.this, ChocolateIcecream.class);
            intent.putExtra("flavor", "Chocolate");
            intent.putExtra("color", "#A76432");
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}
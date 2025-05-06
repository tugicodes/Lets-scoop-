package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cakes extends baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cakes);

        Button blackforestBuyBtn = findViewById(R.id.blackforestBuyBtn);
        Button passionfruitBuyBtn = findViewById(R.id.passionfruitBuyBtn);
        Button chocolateFudgeCakeBuyBtn = findViewById(R.id.ChocolatefudgecakeBuyBtn);

        blackforestBuyBtn.setOnClickListener(v ->
                startActivity(new Intent(Cakes.this, BlackForestCake.class))
        );

        passionfruitBuyBtn.setOnClickListener(v ->
                startActivity(new Intent(Cakes.this, PassionFruitCake.class))
        );

        chocolateFudgeCakeBuyBtn.setOnClickListener(v ->
                startActivity(new Intent(Cakes.this, ChocolateFudgeCake.class))
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}
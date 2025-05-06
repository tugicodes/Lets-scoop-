package com.example.school_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VanillaIcecream extends baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vanilla_icecream);

        // Initialize views
        TextView titleText = findViewById(R.id.titleText);
        TextView priceText = findViewById(R.id.priceText);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        Button backButton = findViewById(R.id.backButton);

        // Get item details
        String itemName = titleText.getText().toString();
        String itemPrice = priceText.getText().toString();
        int itemImage = R.drawable.vanilla_cones;

        backButton.setOnClickListener(v -> finish());

        addToCartButton.setOnClickListener(v -> {
            // Add to cart manager
            CartManager.CartItem item = new CartManager.CartItem(itemName, itemPrice, itemImage);
            CartManager.getInstance().addItem(item);

            Toast.makeText(this,
                    itemName + " added to cart",
                    Toast.LENGTH_SHORT).show();


        });

        // Edge-to-edge handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
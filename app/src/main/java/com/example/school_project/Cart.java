package com.example.school_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Cart extends baseActivity {
    private TextView cartItemsTextView;
    private Button checkoutButton;
    private Button clearCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsTextView = findViewById(R.id.cartItemsTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
        clearCartButton = findViewById(R.id.clearCartButton);

        // Load all items from CartManager
        updateCartDisplay();

        checkoutButton.setOnClickListener(v -> {
            ArrayList<CartManager.CartItem> items = CartManager.getInstance().getItems();
            if (items.isEmpty()) {
                Toast.makeText(Cart.this, "No items to checkout!", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder checkoutSummary = new StringBuilder("Order Summary:\n");
                double total = 0.0;

                for (CartManager.CartItem item : items) {
                    checkoutSummary.append("• ").append(item.getDisplayText()).append("\n");
                    // Parse price (remove $ sign)
                    try {
                        total += Double.parseDouble(item.price.substring(1));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                checkoutSummary.append("\nTotal: $").append(String.format("%.2f", total));
                Toast.makeText(Cart.this, checkoutSummary.toString(), Toast.LENGTH_LONG).show();

                // Here you would typically proceed to payment
            }
        });

        clearCartButton.setOnClickListener(v -> {
            CartManager.getInstance().clearCart();
            updateCartDisplay();
            Toast.makeText(Cart.this, "Cart cleared", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateCartDisplay() {
        ArrayList<CartManager.CartItem> items = CartManager.getInstance().getItems();
        if (items.isEmpty()) {
            cartItemsTextView.setText("Your cart is empty");
        } else {
            StringBuilder cartContent = new StringBuilder();
            for (CartManager.CartItem item : items) {
                cartContent.append("• ").append(item.getDisplayText()).append("\n");
            }
            cartItemsTextView.setText(cartContent.toString());
        }
    }
}
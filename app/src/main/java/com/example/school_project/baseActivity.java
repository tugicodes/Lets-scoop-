package com.example.school_project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class baseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            navigateToHome();
            return true;
        }
        else if (id == R.id.cart) {
            viewCart();
            return true;
        }
        else if (id == R.id.dashboard) {
            openDashboard();
            return true;
        }
        else if (id == R.id.favorites) {
            viewFavorites();
            return true;
        }
        else if (id == R.id.logout) {
            performLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private void viewCart() {
        startActivity(new Intent(this, Cart.class));
    }

    private void openDashboard() {  // Updated method name
        startActivity(new Intent(this, Dashboard.class));
    }

    private void viewFavorites() {
        startActivity(new Intent(this, Favorites.class));
    }

    private void performLogout() {
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    protected void addToCart(String itemName, double price) {
        Intent intent = new Intent(this, Cart.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemPrice", price);
        startActivity(intent);
        Toast.makeText(this, itemName + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
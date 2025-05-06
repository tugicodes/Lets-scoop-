package com.example.school_project;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.school_project.databinding.ActivityChocolateFudgeCakeBinding;

import java.util.Map;

public class ChocolateFudgeCake extends baseActivity {

    private ActivityChocolateFudgeCakeBinding binding;
    private FavoritesManager favoritesManager;
    private static final String CHOCOLATE_CAKE_KEY = "chocolate_fudge_cake_fav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChocolateFudgeCakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        favoritesManager = new FavoritesManager(this);


        updateStarIcon();


        binding.favStarButton.setOnClickListener(v -> {
            boolean isFavorite = !favoritesManager.isFavorite(CHOCOLATE_CAKE_KEY);

            if (isFavorite) {
                favoritesManager.addFavorite(CHOCOLATE_CAKE_KEY,
                        "Chocolate Fudge Cake", "$11.99");
            } else {
                favoritesManager.removeFavorite(CHOCOLATE_CAKE_KEY);
            }

            updateStarIcon();
            Toast.makeText(this,
                    isFavorite ? "Added to favorites!" : "Removed from favorites",
                    Toast.LENGTH_SHORT).show();
        });


        registerForContextMenu(binding.getRoot().findViewById(R.id.chocolatefudgecakecardView));
        registerForContextMenu(binding.getRoot().findViewById(R.id.ChocolatefudgecakePrice));
        registerForContextMenu(binding.getRoot().findViewById(R.id.reviewsContainer));

        binding.AddToCartbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChocolateFudgeCake.this, Cart.class);
            intent.putExtra("cakeItem", "Chocolate Fudge Cake");
            startActivity(intent);
        });
    }
    private void updateStarIcon() {
        boolean isFavorite = favoritesManager.isFavorite(CHOCOLATE_CAKE_KEY);
        binding.favStarButton.setImageResource(
                isFavorite ? R.drawable.baseline_star_24 : R.drawable.sharp_star_border_24
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.chocolatefudgecakecardView) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle("Cake Options");
        }
        else if (v.getId() == R.id.ChocolatefudgecakePrice) {
            menu.add(0, R.id.action_copy_price, 0, "Copy price");
        }
        else if (v.getId() == R.id.reviewsContainer) {
            menu.add(0, R.id.action_report_review, 0, "Report review");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareCake();
            return true;
        }
        else if (id == R.id.action_save_image) {
            Toast.makeText(this, "Save image functionality coming soon", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_copy_price) {
            copyPriceToClipboard();
            return true;
        }
        else if (id == R.id.action_report_review) {
            Toast.makeText(this, "Review reported", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void shareCake() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this delicious Chocolate Fudge Cake!");
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void copyPriceToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Price", "$11.99");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Price copied", Toast.LENGTH_SHORT).show();
    }

    public static class FavoritesManager {
        private final SharedPreferences sharedPrefs;

        public FavoritesManager(Context context) {
            sharedPrefs = context.getSharedPreferences("AppFavorites", Context.MODE_PRIVATE);
        }

        public void addFavorite(String itemId, String name, String price) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(itemId + "_name", name);
            editor.putString(itemId + "_price", price);
            editor.putBoolean(itemId, true);
            editor.apply();
        }

        public void removeFavorite(String itemId) {
            sharedPrefs.edit()
                    .remove(itemId)
                    .remove(itemId + "_name")
                    .remove(itemId + "_price")
                    .apply();
        }

        public boolean isFavorite(String itemId) {
            return sharedPrefs.getBoolean(itemId, false);
        }

        public Map<String, ?> getAllFavorites() {
            return sharedPrefs.getAll();
        }
    }
}
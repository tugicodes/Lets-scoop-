package com.example.school_project;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.school_project.databinding.ActivityBlackForestCakeBinding;

public class BlackForestCake extends baseActivity {

    private ActivityBlackForestCakeBinding binding;
    private ChocolateFudgeCake.FavoritesManager favoritesManager;
    private static final String BLACK_FOREST_KEY = "black_forest_cake_fav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlackForestCakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        favoritesManager = new ChocolateFudgeCake.FavoritesManager(this);


        updateStarIcon();


        binding.favStarButton.setOnClickListener(v -> {
            boolean isFavorite = !favoritesManager.isFavorite(BLACK_FOREST_KEY);

            if (isFavorite) {
                favoritesManager.addFavorite(BLACK_FOREST_KEY,
                        "Black Forest Cake", "$10.99");
            } else {
                favoritesManager.removeFavorite(BLACK_FOREST_KEY);
            }

            updateStarIcon();
            Toast.makeText(this,
                    isFavorite ? "Added to favorites!" : "Removed from favorites",
                    Toast.LENGTH_SHORT).show();
        });

        registerForContextMenu(binding.getRoot().findViewById(R.id.blackforestcakecard));
        registerForContextMenu(binding.getRoot().findViewById(R.id.blackforestPrice));
        registerForContextMenu(binding.getRoot().findViewById(R.id.reviewsContainer));

        binding.AddToCartbtn.setOnClickListener(v -> {
            Intent intent = new Intent(BlackForestCake.this, Cart.class);
            intent.putExtra("cakeItem", "Black Forest Cake");
            startActivity(intent);
        });
    }

    private void updateStarIcon() {
        boolean isFavorite = favoritesManager.isFavorite(BLACK_FOREST_KEY);
        binding.favStarButton.setImageResource(
                isFavorite ? R.drawable.baseline_star_24 : R.drawable.sharp_star_border_24
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.blackforestcakecard) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle("Cake Options");
        }
        else if (v.getId() == R.id.blackforestPrice) {
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
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this delicious Black Forest Cake!");
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void copyPriceToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Price", "$10.99");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Price copied", Toast.LENGTH_SHORT).show();
    }
}
package com.example.school_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;
import java.util.Map;

public class Favorites extends baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ChocolateFudgeCake.FavoritesManager favoritesManager = new ChocolateFudgeCake.FavoritesManager(this);

        MaterialCardView blackForestCard = findViewById(R.id.fav_blackforest_card);
        MaterialCardView passionFruitCard = findViewById(R.id.fav_passionfruit_card);
        MaterialCardView chocolateFudgeCard = findViewById(R.id.fav_chocolatefudge_card);
        Button blackForestBtn = findViewById(R.id.fav_blackforest_btn);
        Button passionFruitBtn = findViewById(R.id.fav_passionfruit_btn);
        Button chocolateFudgeBtn = findViewById(R.id.fav_chocolatefudge_btn);
        TextView emptyState = findViewById(R.id.empty_favorites);
        ImageButton menuButton = findViewById(R.id.menu_button);

        menuButton.setOnClickListener(this::showMainPopupMenu);

        Map<String, ?> favorites = favoritesManager.getAllFavorites();
        boolean hasBlackForest = favorites.containsKey("black_forest_cake_fav");
        boolean hasPassionFruit = favorites.containsKey("passion_fruit_cake_fav");
        boolean hasChocolateFudge = favorites.containsKey("chocolate_fudge_cake_fav");

        blackForestCard.setVisibility(hasBlackForest ? View.VISIBLE : View.GONE);
        passionFruitCard.setVisibility(hasPassionFruit ? View.VISIBLE : View.GONE);
        chocolateFudgeCard.setVisibility(hasChocolateFudge ? View.VISIBLE : View.GONE);

        if (!hasBlackForest && !hasPassionFruit && !hasChocolateFudge) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }

        blackForestBtn.setOnClickListener(v -> startActivity(new Intent(this, BlackForestCake.class)));
        passionFruitBtn.setOnClickListener(v -> startActivity(new Intent(this, PassionFruitCake.class)));
        chocolateFudgeBtn.setOnClickListener(v -> startActivity(new Intent(this, ChocolateFudgeCake.class)));

        blackForestCard.setOnLongClickListener(v -> {
            showCakeItemMenu(v, "black_forest_cake_fav");
            return true;
        });

        passionFruitCard.setOnLongClickListener(v -> {
            showCakeItemMenu(v, "passion_fruit_cake_fav");
            return true;
        });

        chocolateFudgeCard.setOnLongClickListener(v -> {
            showCakeItemMenu(v, "chocolate_fudge_cake_fav");
            return true;
        });

        blackForestCard.setOnClickListener(v -> startActivity(new Intent(this, BlackForestCake.class)));
        passionFruitCard.setOnClickListener(v -> startActivity(new Intent(this, PassionFruitCake.class)));
        chocolateFudgeCard.setOnClickListener(v -> startActivity(new Intent(this, ChocolateFudgeCake.class)));
    }

    private void showMainPopupMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.inflate(R.menu.favorites_popup_menu);

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_clear_all) {
                clearAllFavorites();
                return true;
            } else if (item.getItemId() == R.id.action_settings) {
                openSettings();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showCakeItemMenu(View anchor, String cakeKey) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.inflate(R.menu.favorite_item_menu);

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_remove) {
                new ChocolateFudgeCake.FavoritesManager(this).removeFavorite(cakeKey);
                recreate();
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();
    }

    private void clearAllFavorites() {
        new ChocolateFudgeCake.FavoritesManager(this).getAllFavorites().keySet()
                .forEach(key -> new ChocolateFudgeCake.FavoritesManager(this).removeFavorite(key));
        recreate();
        Toast.makeText(this, "All favorites cleared", Toast.LENGTH_SHORT).show();
    }

    private void openSettings() {
        Toast.makeText(this, "Settings will open here", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
package com.example.school_project;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    public static class CartItem {
        String name;
        String price;
        int imageResId;

        public CartItem(String name, String price, int imageResId) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
        }

        public String getDisplayText() {
            return name + " - " + price;
        }
    }

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public ArrayList<CartItem> getItems() {
        return new ArrayList<>(cartItems); // Return a copy
    }

    public void clearCart() {
        cartItems.clear();
    }

    public int getItemCount() {
        return cartItems.size();
    }
}
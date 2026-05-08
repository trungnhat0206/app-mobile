package com.example.hotrungnhat_2122110432;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("id")
    private String id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("price")
    private double price;
    
    @SerializedName("image")
    private String image;

    @SerializedName("quantity")
    private int quantity;

    public CartItem(String name, double price, String image, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
    public int getQuantity() { return quantity; }
}

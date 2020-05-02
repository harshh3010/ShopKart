package com.codebee.shopkart.Util;

import android.app.Activity;

public class UserApi extends Activity {
    private String Id;
    private String Name;
    private String Email;
    private Double Credits;
    private int Wishlist;
    private int Cart;
    private int Orders;
    private String FCMToken;
    private static UserApi instance;

    public static UserApi getInstance() {
        if(instance == null)
            instance = new UserApi();
        return instance;
    }

    public UserApi() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Double getCredits() {
        return Credits;
    }

    public void setCredits(Double credits) {
        Credits = credits;
    }

    public int getWishlist() {
        return Wishlist;
    }

    public void setWishlist(int wishlist) {
        Wishlist = wishlist;
    }

    public int getCart() {
        return Cart;
    }

    public void setCart(int cart) {
        Cart = cart;
    }

    public int getOrders() {
        return Orders;
    }

    public void setOrders(int orders) {
        Orders = orders;
    }

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }
}

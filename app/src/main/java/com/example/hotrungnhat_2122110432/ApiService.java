package com.example.hotrungnhat_2122110432;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Call<List<User>> login(@retrofit2.http.Query("username") String username, @retrofit2.http.Query("password") String password);

    @POST("users")
    Call<User> registerUser(@Body User user);

    // Giỏ hàng (Cart)
    @GET("cart")
    Call<List<CartItem>> getCart();

    @POST("cart")
    Call<CartItem> addToCart(@Body CartItem cartItem);

    @retrofit2.http.DELETE("cart/{id}")
    Call<Void> removeFromCart(@retrofit2.http.Path("id") String id);
}

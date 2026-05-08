package com.example.hotrungnhat_2122110432;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // ID lấy từ hình ảnh: 69fcb71230ad0a6fd1c00b4f
    // Prefix lấy từ hình ảnh: apimobile
    private static final String BASE_URL = "https://69fcb71230ad0a6fd1c00b4f.mockapi.io/apimobile/";
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

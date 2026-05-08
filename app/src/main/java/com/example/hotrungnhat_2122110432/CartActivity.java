package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private LinearLayout lnCartContainer;
    private TextView tvTotalPrice;
    private Button btnCheckout;

    private double currentTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lnCartContainer = findViewById(R.id.lnCartContainer);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            if (currentTotal > 0) {
                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("totalPrice", currentTotal);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
            }
        });

        // Thêm xử lý nút quay lại trên Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        fetchCart();
    }

    private void fetchCart() {
        ApiClient.getApiService().getCart().enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateCartUI(response.body());
                } else {
                    Toast.makeText(CartActivity.this, "Không thể tải giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCartUI(List<CartItem> cartList) {
        lnCartContainer.removeAllViews();
        currentTotal = 0;

        if (cartList.isEmpty()) {
            tvTotalPrice.setText("0đ");
            return;
        }

        for (CartItem item : cartList) {
            currentTotal += item.getPrice() * item.getQuantity();

            View itemView = LayoutInflater.from(this).inflate(R.layout.item_cart, lnCartContainer, false);
            
            ImageView img = itemView.findViewById(R.id.imgCartProduct);
            TextView tvName = itemView.findViewById(R.id.tvCartProductName);
            TextView tvPrice = itemView.findViewById(R.id.tvCartProductPrice);
            ImageButton btnRemove = itemView.findViewById(R.id.btnRemoveFromCart);

            tvName.setText(item.getName());
            tvPrice.setText(String.format(Locale.getDefault(), "%,.0fđ x %d", item.getPrice(), item.getQuantity()));
            
            // Load image using Glide (hỗ trợ cả URL và Resource ID dạng String)
            String imgData = item.getImage();
            try {
                int resId = Integer.parseInt(imgData);
                img.setImageResource(resId);
            } catch (NumberFormatException e) {
                Glide.with(this).load(imgData).into(img);
            }

            btnRemove.setOnClickListener(v -> {
                removeItem(item.getId());
            });

            lnCartContainer.addView(itemView);
        }

        tvTotalPrice.setText(String.format(Locale.getDefault(), "%,.0fđ", currentTotal));
    }

    private void removeItem(String id) {
        ApiClient.getApiService().removeFromCart(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    fetchCart(); // Tải lại giỏ hàng
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
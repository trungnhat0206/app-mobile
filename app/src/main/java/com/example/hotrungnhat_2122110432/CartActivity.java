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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    // Danh sách giỏ hàng dùng chung toàn app
    public static List<MockProduct> cartList = new ArrayList<>();
    private LinearLayout lnCartContainer;
    private TextView tvTotalPrice;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lnCartContainer = findViewById(R.id.lnCartContainer);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, PaymentActivity.class));
            }
        });

        updateCartUI();

        // Thêm xử lý nút quay lại trên Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void updateCartUI() {
        lnCartContainer.removeAllViews();
        double total = 0;

        for (int i = 0; i < cartList.size(); i++) {
            MockProduct product = cartList.get(i);
            total += product.price;

            View itemView = LayoutInflater.from(this).inflate(R.layout.item_cart, lnCartContainer, false);
            
            ImageView img = itemView.findViewById(R.id.imgCartProduct);
            TextView tvName = itemView.findViewById(R.id.tvCartProductName);
            TextView tvPrice = itemView.findViewById(R.id.tvCartProductPrice);
            ImageButton btnRemove = itemView.findViewById(R.id.btnRemoveFromCart);

            img.setImageResource(product.imageRes);
            tvName.setText(product.name);
            tvPrice.setText(String.format(Locale.getDefault(), "%,.0fđ", product.price));

            final int index = i;
            btnRemove.setOnClickListener(v -> {
                cartList.remove(index);
                updateCartUI();
            });

            lnCartContainer.addView(itemView);
        }

        tvTotalPrice.setText(String.format(Locale.getDefault(), "%,.0fđ", total));
    }

    // Lớp dữ liệu đơn giản nằm trong Activity để dễ quản lý
    public static class MockProduct {
        String name;
        double price;
        int imageRes;

        public MockProduct(String name, double price, int imageRes) {
            this.name = name;
            this.price = price;
            this.imageRes = imageRes;
        }
    }
}
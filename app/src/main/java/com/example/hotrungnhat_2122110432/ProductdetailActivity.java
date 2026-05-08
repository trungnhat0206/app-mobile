package com.example.hotrungnhat_2122110432;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ProductdetailActivity extends AppCompatActivity {

    private ImageView imgProductDetail;
    private TextView tvProductNameDetail, tvProductPriceDetail, tvProductDescDetail;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productdetail);

        // Ánh xạ View
        imgProductDetail = findViewById(R.id.imgProductDetail);
        tvProductNameDetail = findViewById(R.id.tvProductNameDetail);
        tvProductPriceDetail = findViewById(R.id.tvProductPriceDetail);
        tvProductDescDetail = findViewById(R.id.tvProductDescDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Lấy dữ liệu truyền từ Intent
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0);
        String desc = getIntent().getStringExtra("desc");
        int image = getIntent().getIntExtra("image", android.R.drawable.ic_menu_gallery);

        // Hiển thị dữ liệu lên giao diện
        if (name != null) {
            tvProductNameDetail.setText(name);
            tvProductPriceDetail.setText(String.format(Locale.getDefault(), "%,.0fđ", price));
            tvProductDescDetail.setText(desc);
            imgProductDetail.setImageResource(image);
            
            // Xử lý nút Thêm vào giỏ
            btnAddToCart.setOnClickListener(v -> {
                CartItem cartItem = new CartItem(name, price, String.valueOf(image), 1);
                ApiClient.getApiService().addToCart(cartItem).enqueue(new retrofit2.Callback<CartItem>() {
                    @Override
                    public void onResponse(retrofit2.Call<CartItem> call, retrofit2.Response<CartItem> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductdetailActivity.this, "Đã thêm " + name + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductdetailActivity.this, "Lỗi khi thêm vào giỏ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<CartItem> call, Throwable t) {
                        Toast.makeText(ProductdetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        // Thiết lập Padding hệ thống để không bị mất ID "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nút quay lại trên Toolbar
        findViewById(R.id.toolbarDetail).setOnClickListener(v -> finish());
    }
}
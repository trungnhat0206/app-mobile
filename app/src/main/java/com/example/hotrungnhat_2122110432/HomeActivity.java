package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcomeUser;
    private CardView cardProducts, cardCart, cardPayment, cardLogout;
    private ImageButton btnProfile;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        
        // Sửa lỗi "main" ID không tồn tại trong CoordinatorLayout mới
        // Trong activity_home.xml mới, tôi không đặt ID cho CoordinatorLayout
        // Hãy thêm ID hoặc sử dụng View gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Ánh xạ View chính xác theo layout mới
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        btnProfile = findViewById(R.id.btnProfile);
        cardProducts = findViewById(R.id.cardProducts);
        cardCart = findViewById(R.id.cardCart);
        cardPayment = findViewById(R.id.cardPayment);
        cardLogout = findViewById(R.id.cardLogout);

        // Hiển thị tên người dùng (Lấy từ SharedPreferences đã lưu khi đăng nhập)
        String username = sharedPreferences.getString("username", "Nhật");
        tvWelcomeUser.setText("Hi, " + username + "!");

        // Cài đặt sự kiện click cho các Card
        if (cardProducts != null) {
            cardProducts.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intent);
            });
        }

        if (cardCart != null) {
            cardCart.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            });
        }

        if (cardPayment != null) {
            cardPayment.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
                startActivity(intent);
            });
        }

        if (cardLogout != null) {
            cardLogout.setOnClickListener(v -> {
                // Đăng xuất
                Toast.makeText(HomeActivity.this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
        
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                Toast.makeText(this, "Tính năng Profile đang cập nhật!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
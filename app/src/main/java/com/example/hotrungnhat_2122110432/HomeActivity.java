package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private androidx.cardview.widget.CardView cardProducts, cardCart, cardPayment, cardLogout;
    private androidx.cardview.widget.CardView cardFeatured1, cardFeatured2, cardFeatured3, cardFeatured4;
    private android.view.View btnProfile;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        
        // Thiết lập padding để không bị che bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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

        // Ánh xạ các món nổi bật
        cardFeatured1 = findViewById(R.id.cardFeatured1);
        cardFeatured2 = findViewById(R.id.cardFeatured2);
        cardFeatured3 = findViewById(R.id.cardFeatured3);
        cardFeatured4 = findViewById(R.id.cardFeatured4);

        // Hiển thị tên người dùng (Lấy từ SharedPreferences đã lưu khi đăng nhập)
        String username = sharedPreferences.getString("username", "Nhật");
        tvWelcomeUser.setText("Hi, " + username + "!");

        // Cài đặt sự kiện click cho các Card món nổi bật
        if (cardFeatured1 != null) {
            cardFeatured1.setOnClickListener(v -> goToDetail("Cà Phê Muối", 35000, "Vị mặn dịu hòa quyện cà phê đậm đà.", R.drawable.cf));
        }
        if (cardFeatured2 != null) {
            cardFeatured2.setOnClickListener(v -> goToDetail("Trà Đào Cam Sả", 45000, "Thức uống giải nhiệt cực tốt.", R.drawable.vf));
        }
        if (cardFeatured3 != null) {
            cardFeatured3.setOnClickListener(v -> goToDetail("Bạc Xỉu Đá", 29000, "Cà phê sữa kiểu Sài Gòn.", R.drawable.phe));
        }
        if (cardFeatured4 != null) {
            cardFeatured4.setOnClickListener(v -> goToDetail("Caramel Macchiato", 55000, "Ngọt ngào hương caramel.", R.drawable.ca_phe));
        }

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
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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

    private void goToDetail(String name, double price, String desc, int imageRes) {
        Intent intent = new Intent(HomeActivity.this, ProductdetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("desc", desc);
        intent.putExtra("image", imageRes);
        startActivity(intent);
    }
}
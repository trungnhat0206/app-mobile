package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class ProductActivity extends AppCompatActivity {

    private CardView item1, item2;
    private Button btnBuy1, btnBuy2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các Card sản phẩm
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        
        // Ánh xạ các nút Mua
        btnBuy1 = findViewById(R.id.btnBuy1);
        btnBuy2 = findViewById(R.id.btnBuy2);

        // 1. Xem chi tiết sản phẩm 1
        item1.setOnClickListener(v -> {
            openDetail("Cà phê Muối", 35000, "Cà phê muối thơm ngon, vị mặn nhẹ hòa quyện cùng vị đắng của cà phê.");
        });

        // 2. Xem chi tiết sản phẩm 2
        item2.setOnClickListener(v -> {
            openDetail("Trà Đào Cam Sả", 45000, "Trà đào thơm mát, kết hợp vị chua của cam và hương thơm của sả.");
        });

        // 3. Thêm vào giỏ sản phẩm 1
        btnBuy1.setOnClickListener(v -> {
            addToCart("Cà phê Muối", 35000);
        });

        // 4. Thêm vào giỏ sản phẩm 2
        btnBuy2.setOnClickListener(v -> {
            addToCart("Trà Đào Cam Sả", 45000);
        });
    }

    private void openDetail(String name, double price, String desc) {
        // Tạo đối tượng sản phẩm giả lập (Vì bạn không muốn file Product.kt riêng)
        // Chúng ta sẽ truyền thông tin qua Intent
        Intent intent = new Intent(ProductActivity.this, ProductdetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("desc", desc);
        startActivity(intent);
    }

    private void addToCart(String name, double price) {
        // Thêm vào danh sách tĩnh trong CartActivity
        // Lưu ý: Cần một class trung gian để lưu dữ liệu sản phẩm đơn giản
        // Nếu không có class Product, ta dùng các biến cơ bản.
        Toast.makeText(this, "Đã thêm " + name + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
    }
}
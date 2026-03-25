package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        
        // Đã xóa phần kiểm tra tự động đăng nhập (Auto-login) để ứng dụng luôn hiện trang Login khi mở

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các View
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        // Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(v -> {
            String inputUser = edtUsername.getText().toString().trim();
            String inputPass = edtPassword.getText().toString().trim();

            if (inputUser.isEmpty() || inputPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy thông tin tài khoản đã đăng ký từ bộ nhớ
            String registeredUser = sharedPreferences.getString("registered_user", "");
            String registeredPass = sharedPreferences.getString("registered_pass", "");

            // Kiểm tra tài khoản (admin hoặc tài khoản đăng ký)
            boolean isAdmin = inputUser.equals("admin") && inputPass.equals("123456");
            boolean isUser = inputUser.equals(registeredUser) && inputPass.equals(registeredPass) && !registeredUser.isEmpty();

            if (isAdmin || isUser) {
                // Lưu tên người dùng để hiển thị ở trang chủ
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", inputUser);
                editor.apply();

                Toast.makeText(MainActivity2.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                goToHome();
            } else {
                Toast.makeText(MainActivity2.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển sang trang Đăng ký
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void goToHome() {
        Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
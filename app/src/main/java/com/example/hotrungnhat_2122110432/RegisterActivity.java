package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLoginSubmit;
    private TextView tvBackToMain;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        edtUsername = findViewById(R.id.edtLoginUsername);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        tvBackToMain = findViewById(R.id.tvBackToMain);
        progressBar = findViewById(R.id.progressBar);

        if (btnLoginSubmit != null) {
            btnLoginSubmit.setOnClickListener(v -> {
                String user = edtUsername.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Hiển thị "đường truyền" (ProgressBar) và ẩn nút bấm
                progressBar.setVisibility(View.VISIBLE);
                btnLoginSubmit.setEnabled(false);

                // Giả lập thời gian truyền dữ liệu (2 giây)
                new Handler().postDelayed(() -> {
                    // Kiểm tra đăng nhập sau khi "truyền" xong
                    if (user.equals("nhatlk2303") && pass.equals("123456")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("username", user);
                        editor.apply();

                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        
                        // Chuyển sang trang Home
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Nếu sai, hiện lại nút và ẩn Progress
                        progressBar.setVisibility(View.GONE);
                        btnLoginSubmit.setEnabled(true);
                        Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000); // 2000ms = 2 giây
            });
        }

        if (tvBackToMain != null) {
            tvBackToMain.setOnClickListener(v -> finish());
        }
    }
}
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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtRegUsername, edtRegEmail, edtRegPassword, edtRegConfirmPassword;
    private Button btnRegisterSubmit;
    private TextView tvBackToLogin;
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

        // Ánh xạ View chính xác theo activity_register.xml
        edtRegUsername = findViewById(R.id.edtRegUsername);
        edtRegEmail = findViewById(R.id.edtRegEmail);
        edtRegPassword = findViewById(R.id.edtRegPassword);
        edtRegConfirmPassword = findViewById(R.id.edtRegConfirmPassword);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        if (btnRegisterSubmit != null) {
            btnRegisterSubmit.setOnClickListener(v -> {
                String user = edtRegUsername.getText().toString().trim();
                String email = edtRegEmail.getText().toString().trim();
                String pass = edtRegPassword.getText().toString().trim();
                String confirmPass = edtRegConfirmPassword.getText().toString().trim();

                if (user.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu thông tin đăng ký
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("registered_user", user);
                editor.putString("registered_pass", pass);
                editor.apply();

                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                
                // Quay lại màn hình đăng nhập
                finish();
            });
        }

        if (tvBackToLogin != null) {
            tvBackToLogin.setOnClickListener(v -> finish());
        }
    }
}

package com.example.hotrungnhat_2122110432;

import android.content.Intent;
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

import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private EditText edtAddress, edtPhone;
    private TextView tvPaymentTotal;
    private Button btnConfirmPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        tvPaymentTotal = findViewById(R.id.tvPaymentTotal);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        // Tính tổng tiền
        double total = 0;
        for (CartActivity.MockProduct p : CartActivity.cartList) {
            total += p.price;
        }
        tvPaymentTotal.setText(String.format(Locale.getDefault(), "%,.0fđ", total));

        btnConfirmPayment.setOnClickListener(v -> {
            String addr = edtAddress.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (addr.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Đặt hàng thành công! Cảm ơn bạn.", Toast.LENGTH_LONG).show();
            
            // Xóa giỏ hàng sau khi thanh toán
            CartActivity.cartList.clear();
            
            // Quay về Home
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Nút quay lại trên Toolbar (nếu có id là toolbarPayment)
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}

package com.example.hotrungnhat_2122110432;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {

    private EditText edtSearch;
    private GridLayout gridProducts;
    private List<ProductItem> fullProductList;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        edtSearch = findViewById(R.id.edtSearchProducts);
        gridProducts = findViewById(R.id.gridProducts);

        // Khởi tạo danh sách sản phẩm
        initProductList();

        // Hiển thị sản phẩm ban đầu
        renderProducts(fullProductList);

        // Xử lý tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString(), currentCategory);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Xử lý lọc theo Category
        findViewById(R.id.btnCatAll).setOnClickListener(v -> updateCategory("All"));
        findViewById(R.id.btnCatCoffee).setOnClickListener(v -> updateCategory("Coffee"));
        findViewById(R.id.btnCatTea).setOnClickListener(v -> updateCategory("Tea"));
        findViewById(R.id.btnCatCake).setOnClickListener(v -> updateCategory("Bakery"));

        findViewById(R.id.btnBackProducts).setOnClickListener(v -> finish());
    }

    private void initProductList() {
        fullProductList = new ArrayList<>();
        // Sử dụng các hình ảnh bạn đã thêm vào drawable: cf, vf, phe
        fullProductList.add(new ProductItem("Cà phê Muối", 35000, R.drawable.cf, "Vị mặn dịu hòa quyện cà phê đậm đà.", "Coffee"));
        fullProductList.add(new ProductItem("Trà Đào Cam Sả", 45000, R.drawable.vf, "Thức uống giải nhiệt cực tốt.", "Tea"));
        fullProductList.add(new ProductItem("Bạc Xỉu", 29000, R.drawable.phe, "Cà phê sữa kiểu Sài Gòn.", "Coffee"));
        fullProductList.add(new ProductItem("Caramel Macchiato", 55000, R.drawable.cf, "Ngọt ngào hương caramel.", "Coffee"));
        fullProductList.add(new ProductItem("Trà Vải", 38000, R.drawable.vf, "Trà vải thơm mát lành.", "Tea"));
        fullProductList.add(new ProductItem("Bánh Mì Pate", 25000, R.drawable.phe, "Đặc sản đường phố Việt Nam.", "Bakery"));
        fullProductList.add(new ProductItem("Croissant Choco", 32000, R.drawable.cf, "Bánh sừng bò nhân socola.", "Bakery"));
    }

    private void updateCategory(String category) {
        currentCategory = category;
        filterProducts(edtSearch.getText().toString(), currentCategory);
    }

    private void renderProducts(List<ProductItem> list) {
        gridProducts.removeAllViews();
        for (ProductItem item : list) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_product_card, gridProducts, false);
            
            TextView tvName = view.findViewById(R.id.tvCardName);
            TextView tvPrice = view.findViewById(R.id.tvCardPrice);
            ImageView img = view.findViewById(R.id.imgCard);
            Button btnBuy = view.findViewById(R.id.btnCardBuy);

            tvName.setText(item.name);
            tvPrice.setText(String.format(Locale.getDefault(), "%,.0fđ", item.price));
            img.setImageResource(item.imageRes); // Hiển thị hình ảnh
            
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProductdetailActivity.class);
                intent.putExtra("name", item.name);
                intent.putExtra("price", item.price);
                intent.putExtra("desc", item.desc);
                intent.putExtra("image", item.imageRes); // Truyền ảnh sang trang chi tiết
                startActivity(intent);
            });

            btnBuy.setOnClickListener(v -> {
                CartActivity.cartList.add(new CartActivity.MockProduct(item.name, item.price, item.imageRes));
                Toast.makeText(this, "Đã thêm " + item.name + " vào giỏ!", Toast.LENGTH_SHORT).show();
            });

            gridProducts.addView(view);
        }
    }

    private void filterProducts(String query, String category) {
        List<ProductItem> filteredList = new ArrayList<>();
        for (ProductItem item : fullProductList) {
            boolean matchesQuery = item.name.toLowerCase().contains(query.toLowerCase());
            boolean matchesCategory = category.equals("All") || item.category.equals(category);
            
            if (matchesQuery && matchesCategory) {
                filteredList.add(item);
            }
        }
        renderProducts(filteredList);
    }

    private static class ProductItem {
        String name;
        double price;
        int imageRes;
        String desc;
        String category;

        ProductItem(String name, double price, int imageRes, String desc, String category) {
            this.name = name;
            this.price = price;
            this.imageRes = imageRes;
            this.desc = desc;
            this.category = category;
        }
    }
}

package com.example.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.product.Client.Client;
import com.example.product.product.Product;
import com.example.product.product.ProductService;

import java.io.IOException;
import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    boolean isUpdate;
    private EditText name,price;
    private Long productId;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        TextView title = findViewById(R.id.title);
        Button button = findViewById(R.id.button);
        name = findViewById(R.id.productNameEditText);
        price = findViewById(R.id.priceEditText);


        Intent intent = getIntent();

        userId = intent.getLongExtra("user_id",0);

        isUpdate = intent.getBooleanExtra("update",false);

        if (isUpdate) {
            title.setText("Update The Product");
            button.setText("UPDATE");
            name.setText(intent.getStringExtra("product_name"));
            price.setText(intent.getStringExtra("product_price"));
            productId = intent.getLongExtra("product_id",0);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().length()>0 && price.getText().toString().length()>0){
                    if (isUpdate) update(); else add();
                }else {
                    Toast.makeText(AddProductActivity.this,"Please fill all inputs.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void add(){
        Product product = new Product(name.getText().toString(),BigDecimal.valueOf(Long.parseLong(price.getText().toString())),userId);
        Client.getRetrofit().create(ProductService.class).add(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddProductActivity.this,"Product Created",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

    }

    private void update(){

        Product product = new Product(productId,name.getText().toString(), BigDecimal.valueOf(Double.parseDouble(price.getText().toString())),userId);
        Client.getRetrofit().create(ProductService.class).update(userId,product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddProductActivity.this,"Product Updated",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Toast.makeText(AddProductActivity.this,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AddProductActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
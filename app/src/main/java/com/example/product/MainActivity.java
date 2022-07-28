package com.example.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.product.Client.Client;
import com.example.product.user.User;
import com.example.product.user.UserService;

import java.sql.ClientInfoStatus;

import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http2.Http2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name, surname;
        Button login;

        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        login = findViewById(R.id.loginButton);


        login.setOnClickListener(view -> {
            if (name.getText().toString().length() > 0 && surname.getText().toString().length() > 0)
                findUser(new User(name.getText().toString(), surname.getText().toString()));
            else
                Toast.makeText(MainActivity.this,"Please fill all inputs.",Toast.LENGTH_SHORT).show();
        });

    }


    private void findUser(User user) {

        Client.getRetrofit().create(UserService.class).findUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    intent.putExtra("user_id", response.body().getId());
                    startActivity(intent);
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
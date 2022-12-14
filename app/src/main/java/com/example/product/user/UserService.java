package com.example.product.user;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/find")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<User> findUser(@Body User user);
}

package com.example.product.product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    @GET("product")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<List<Product>> getProducts();

    @DELETE("product/{productId}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> delete(@Header("user_id") Long userId, @Path("productId") Long productId);

    @PUT("product")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Product> update(@Header("user_id") Long userId, @Body Product product);

    @POST("product")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Product> add(@Body Product product);
}

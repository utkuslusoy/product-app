package com.example.product.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.product.AddProductActivity;
import com.example.product.Client.Client;
import com.example.product.R;
import com.example.product.product.Product;
import com.example.product.product.ProductService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    private Long userId;

    public ProductAdapter(List<Product> productList, Context context, Long userId) {
        this.productList = productList;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = productList.get(holder.getBindingAdapterPosition());

        holder.price.setText(String.valueOf(product.getPrice()));
        holder.id.setText(String.valueOf(product.getId()));
        holder.name.setText(product.getName());
        holder.userId.setText(String.valueOf(product.getUserId()));


        holder.delete.setOnClickListener(view -> {
            deleteProduct(product);
        });

        holder.update.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddProductActivity.class);
            intent.putExtra("update",true);
            intent.putExtra("product_name",product.getName());
            intent.putExtra("product_id",product.getId());
            intent.putExtra("product_price",product.getPrice().toString());
            intent.putExtra("user_id",userId);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button delete, update;
        public TextView name, id, price,userId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.deleteButton);
            update = itemView.findViewById(R.id.updateButton);
            name = itemView.findViewById(R.id.productName);
            id = itemView.findViewById(R.id.productId);
            price = itemView.findViewById(R.id.price);
            userId = itemView.findViewById(R.id.userId);


        }
    }

    private void deleteProduct(Product product){

        Client.getRetrofit().create(ProductService.class).delete(userId,product.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context,"Product Deleted",Toast.LENGTH_SHORT).show();
                    int position = productList.indexOf(product);
                    productList.remove(product);
                    notifyItemRemoved(position);
                }else {
                    try {
                        Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}

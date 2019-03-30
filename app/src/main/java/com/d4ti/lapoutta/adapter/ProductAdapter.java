package com.d4ti.lapoutta.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.DetailProductActivity;
import com.d4ti.lapoutta.activity.store.ChangeProductActivity;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private Context context;
    private List<Product> products;
    private int id_customer;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        id_customer = SaveSharedPreference.getIdUser(context);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtNameProduct.setText(getProducts().get(position).getName());
        holder.txtPriceProduct.setText("Rp " + Double.toString(getProducts().get(position).getPrice()));
        //Glide.with(context).load(getProducts().get(position).getImage()).into(holder.imgProduct);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_customer!= getProducts().get(position).getStore().getId_customer()) {
                    Intent intentProduct = new Intent(context.getApplicationContext(), DetailProductActivity.class);
                    intentProduct.putExtra("ID_PRODUCT", getProducts().get(position).getId());
                    intentProduct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intentProduct);
                }else{
                    Intent intentProduct = new Intent(context.getApplicationContext(), ChangeProductActivity.class);
                    intentProduct.putExtra("ID_PRODUCT", getProducts().get(position).getId());
                    intentProduct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intentProduct);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtNameProduct;
        TextView txtPriceProduct;
        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgProduct = itemView.findViewById(R.id.img_product);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);
            txtPriceProduct = itemView.findViewById(R.id.txt_price_product);
        }
    }
}

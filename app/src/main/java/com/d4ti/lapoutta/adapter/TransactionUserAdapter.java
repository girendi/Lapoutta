package com.d4ti.lapoutta.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.DetailProductActivity;
import com.d4ti.lapoutta.activity.RincianActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.DetailTransaction;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionUserAdapter extends RecyclerView.Adapter<TransactionUserAdapter.ViewHolder> {

    String url="http://192.168.43.157:1337/images/uploads/";

    private Activity activity;
    private List<Transaction> transactions;
    private BaseApiService baseApiService;

    public TransactionUserAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_transaction_user, viewGroup, false);
        baseApiService = UtilsApi.getAPIService();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        baseApiService.listDetailTransaction(getTransactions().get(i).getId()).enqueue(new Callback<List<DetailTransaction>>() {
            @Override
            public void onResponse(Call<List<DetailTransaction>> call, Response<List<DetailTransaction>> response) {
                if (response.isSuccessful()){
                    List<DetailTransaction> detailTransactions = response.body();
                    if (!detailTransactions.isEmpty()){
                        viewHolder.txtCount.setText(String.valueOf(detailTransactions.get(0).getQuantity()));
                        viewHolder.txtPrice.setText(String.valueOf(detailTransactions.get(0).getSub_total()));
                        baseApiService.detailProduct(detailTransactions.get(0).getId_product()).enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (response.isSuccessful()){
                                    final List<Product> products = response.body();
                                    if (!products.isEmpty()){
                                        Log.d("Name Product", products.get(0).getName());
                                        viewHolder.txtNameProduct.setText(products.get(0).getName());
                                        viewHolder.txtNameStore.setText(products.get(0).getStore().getName());
                                        Glide.with(activity).load(url + products.get(0).getImage()).into(viewHolder.imgProduct);

                                        viewHolder.txtNameProduct.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(activity, DetailProductActivity.class);
                                                intent.putExtra("ID_PRODUCT", products.get(0).getId());
                                                activity.startActivity(intent);
                                                activity.finish();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DetailTransaction>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RincianActivity.class);
                intent.putExtra("ID_TRANSACTION", getTransactions().get(i).getId());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameStore;
        TextView txtNameProduct;
        TextView txtPrice;
        TextView txtCount;
        ImageView imgProduct;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtNameStore = itemView.findViewById(R.id.txt_name_store);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtCount = itemView.findViewById(R.id.txt_count);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }
}

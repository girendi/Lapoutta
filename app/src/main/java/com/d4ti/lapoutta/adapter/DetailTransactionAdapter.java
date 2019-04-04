package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.DetailTransaction;
import com.d4ti.lapoutta.modal.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionAdapter extends RecyclerView.Adapter<DetailTransactionAdapter.ViewHolder> {

    String url="http://192.168.43.157:1337/images/uploads/";

    private Context context;
    private List<DetailTransaction> detailTransactions;
    private BaseApiService baseApiService;

    public DetailTransactionAdapter(Context context) {
        this.context = context;
    }

    public List<DetailTransaction> getDetailTransactions() {
        return detailTransactions;
    }

    public void setDetailTransactions(List<DetailTransaction> detailTransactions) {
        this.detailTransactions = detailTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_detailtransaction, viewGroup, false);
        baseApiService = UtilsApi.getAPIService();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtCount.setText(String.valueOf(getDetailTransactions().get(i).getQuantity()));
        viewHolder.txtPrice.setText(String.valueOf(getDetailTransactions().get(i).getSub_total()));
        baseApiService.detailProduct(getDetailTransactions().get(i).getId_product()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    List<Product> products = response.body();
                    if (!products.isEmpty()){
                        viewHolder.txtNameProduct.setText(products.get(0).getName());
                        viewHolder.txtNameStore.setText(products.get(0).getStore().getName());
                        Glide.with(context).load(url + products.get(0).getImage()).into(viewHolder.imgProduct);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameStore;
        TextView txtNameProduct;
        TextView txtPrice;
        TextView txtCount;
        ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameStore = itemView.findViewById(R.id.txt_name_store);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtCount = itemView.findViewById(R.id.txt_count);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }
}

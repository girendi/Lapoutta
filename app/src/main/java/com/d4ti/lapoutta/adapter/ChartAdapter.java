package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Chart;
import com.d4ti.lapoutta.modal.Product;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder>{

    private Context context;
    private List<Chart> charts;
    private BaseApiService baseApiService;

    public ChartAdapter(Context context) {
        this.context = context;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    @NonNull
    @Override
    public ChartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_chart, viewGroup, false);
        baseApiService = UtilsApi.getAPIService();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChartAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtCount.setText(getCharts().get(i).getQuantity());
        baseApiService.detailProduct(getCharts().get(i).getId_product()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    List<Product> list = response.body();
                    if (!list.isEmpty()){
                        viewHolder.txtNameStore.setText(list.get(0).getStore().getName());
                        viewHolder.txtNameProduct.setText(list.get(0).getName());
                        viewHolder.txtPrice.setText(Double.toString(list.get(0).getPrice()));
                        Glide.with(context).load(list.get(0).getImage()).into(viewHolder.imgProduct);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
        viewHolder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getCharts().get(i).getQuantity();
                count = count + 1 ;
                baseApiService.updateChart(getCharts().get(i).getId(), count).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("Success", "Jumlah Berhasil Di Ubah");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
            }
        });

        viewHolder.imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getCharts().get(i).getQuantity();
                count = count - 1 ;
                baseApiService.updateChart(getCharts().get(i).getId(), count).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("Success", "Jumlah Berhasil Di Ubah");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
            }
        });
        viewHolder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseApiService.deleteChart(getCharts().get(i).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("Sukses", "Berhasil Menghapus Chart");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return charts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameStore;
        TextView txtNameProduct;
        TextView txtPrice;
        TextView txtCount;
        CheckBox checkBox;
        ImageView imgProduct;
        ImageButton imgAdd, imgDecrease, imgClose;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameStore = itemView.findViewById(R.id.txt_name_store);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtCount = itemView.findViewById(R.id.txt_count_product);
            checkBox = itemView.findViewById(R.id.cb_chart);
            imgProduct = itemView.findViewById(R.id.img_product);
            imgAdd = itemView.findViewById(R.id.img_btn_add);
            imgDecrease = itemView.findViewById(R.id.img_btn_decrease);
            imgClose = itemView.findViewById(R.id.btn_close);
        }
    }
}

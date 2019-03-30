package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.ChartActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Chart;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder>{

    private ChartActivity chartActivity;
    private List<Chart> charts;
    private BaseApiService baseApiService;
    private double total_price = 0;
    private int id_customer;

    public ChartAdapter(ChartActivity chartActivity) {
        this.chartActivity = chartActivity;
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
        View view = LayoutInflater.from(chartActivity).inflate(R.layout.item_list_chart, viewGroup, false);
        baseApiService = UtilsApi.getAPIService();
        id_customer = SaveSharedPreference.getIdUser(chartActivity);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChartAdapter.ViewHolder viewHolder, final int i) {

        baseApiService.detailProduct(getCharts().get(i).getId_product()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    List<Product> list = response.body();
                    if (!list.isEmpty()){
                        viewHolder.txtNameStore.setText(list.get(0).getStore().getName());
                        viewHolder.txtNameProduct.setText(list.get(0).getName());
                        viewHolder.txtPrice.setText(Double.toString(list.get(0).getPrice()));
                        //Glide.with(context).load(list.get(0).getImage()).into(viewHolder.imgProduct);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });

        viewHolder.btn_quantity.setNumber(String.valueOf(getCharts().get(i).getQuantity()));

        viewHolder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                baseApiService.updateChart(getCharts().get(i).getId(), newValue).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            baseApiService.getListChart(id_customer).enqueue(new Callback<List<Chart>>() {
                                @Override
                                public void onResponse(Call<List<Chart>> call, Response<List<Chart>> response) {
                                    if (response.isSuccessful()){
                                        List<Chart> charts = response.body();
                                        if (!charts.isEmpty()){
                                            for (int i = 0; i < charts.size(); i++){
                                                final int quantity = charts.get(i).getQuantity();
                                                if (charts.get(i).isIs_active() == 1){
                                                    baseApiService.detailProduct(charts.get(i).getId_product()).enqueue(new Callback<List<Product>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                                            if (response.isSuccessful()){
                                                                List<Product> list = response.body();
                                                                if (!list.isEmpty()){
                                                                    total_price = total_price + (quantity * list.get(0).getPrice());
                                                                    Log.i("Price", Double.toString(total_price));
                                                                    chartActivity.txt_total_price.setText(Double.toString(total_price));
                                                                }
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<List<Product>> call, Throwable t) {
                                                            Log.e("Error Message", t.getMessage());
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<Chart>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
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
                            baseApiService.getListChart(id_customer).enqueue(new Callback<List<Chart>>() {
                                @Override
                                public void onResponse(Call<List<Chart>> call, Response<List<Chart>> response) {
                                    if (response.isSuccessful()){
                                        charts = response.body();
                                        if (!charts.isEmpty()){
                                            chartActivity.rv_chart.setLayoutManager(new LinearLayoutManager(chartActivity));
                                            ChartAdapter chartAdapter = new ChartAdapter(chartActivity);
                                            chartAdapter.setCharts(charts);
                                            chartActivity.rv_chart.setAdapter(chartAdapter);
                                            for (int i = 0; i < charts.size(); i++){
                                                final int quantity = charts.get(i).getQuantity();
                                                if (charts.get(i).isIs_active() == 1){
                                                    baseApiService.detailProduct(charts.get(i).getId_product()).enqueue(new Callback<List<Product>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                                            if (response.isSuccessful()){
                                                                List<Product> list = response.body();
                                                                if (!list.isEmpty()){
                                                                    total_price = total_price + (quantity * list.get(0).getPrice());
                                                                    Log.i("Price", Double.toString(total_price));
                                                                    chartActivity.txt_total_price.setText(Double.toString(total_price));
                                                                }
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<List<Product>> call, Throwable t) {
                                                            Log.e("Error Message", t.getMessage());
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<Chart>> call, Throwable t) {
                                    Log.e("Error Message", t.getMessage());
                                }
                            });
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
        CheckBox checkBox;
        ImageView imgProduct;
        ImageButton imgClose;
        ElegantNumberButton btn_quantity;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameStore = itemView.findViewById(R.id.txt_name_store);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            checkBox = itemView.findViewById(R.id.cb_chart);
            imgProduct = itemView.findViewById(R.id.img_product);
            imgClose = itemView.findViewById(R.id.btn_close);
            btn_quantity = itemView.findViewById(R.id.btn_quantity);
        }
    }
}

package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.ChartAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Chart;
import com.d4ti.lapoutta.modal.DetailTransaction;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView txt_header, txt_data_empty;
    public TextView txt_total_price;
    private Button btn_bought;
    public RecyclerView rv_chart;
    public BaseApiService baseApiService;
    private int id;
    private double total_price = 0;
    private List<Chart> charts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        if (SaveSharedPreference.getLoggedStatus(this) != true){
            Toast.makeText(this, "Status : " + SaveSharedPreference.getLoggedStatus(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }else{
            initComponent();
            setData();
            txt_header.setText("Keranjang Saya");

            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });

            btn_bought.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkout();
                }
            });
        }
    }

    private void checkout() {
        baseApiService.checkout(id).enqueue(new Callback<List<DetailTransaction>>() {
            @Override
            public void onResponse(Call<List<DetailTransaction>> call, Response<List<DetailTransaction>> response) {
                if (response.isSuccessful()){
                    List<DetailTransaction> detailTransactions = response.body();
                    if (!detailTransactions.isEmpty()){
                        int id_transaction = detailTransactions.get(0).getId_transaction();
                        Intent intent = new Intent(getApplicationContext(), MetodeActivity.class);
                        intent.putExtra("ID_TRANSACTION", id_transaction);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DetailTransaction>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        id = SaveSharedPreference.getIdUser(this);
        baseApiService = UtilsApi.getAPIService();
        rv_chart = findViewById(R.id.list_chart);
        imgLogo = findViewById(R.id.image_home);
        txt_total_price = findViewById(R.id.txt_total_price);
        txt_data_empty = findViewById(R.id.data_empty);
        btn_bought = findViewById(R.id.btn_bought);
        txt_header = findViewById(R.id.txt_header);
    }

    private void setData() {
        baseApiService.getListChart(id).enqueue(new Callback<List<Chart>>() {
            @Override
            public void onResponse(Call<List<Chart>> call, Response<List<Chart>> response) {
                if (response.isSuccessful()){
                    charts = response.body();
                    if (!charts.isEmpty()){
                        txt_data_empty.setVisibility(View.INVISIBLE);
                        rv_chart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ChartAdapter chartAdapter = new ChartAdapter(ChartActivity.this);
                        chartAdapter.setCharts(charts);
                        rv_chart.setAdapter(chartAdapter);

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
                                                txt_total_price.setText(Double.toString(total_price));
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

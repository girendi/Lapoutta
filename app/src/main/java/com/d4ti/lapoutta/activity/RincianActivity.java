package com.d4ti.lapoutta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.DetailTransactionAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.DetailTransaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianActivity extends AppCompatActivity {

    private RecyclerView rv_product;
    private TextView tv_status_pesanan, tv_status_pengiriman, tv_address, tv_total, tv_sub_total;

    private BaseApiService baseApiService;
    private List<DetailTransaction> detailTransactions;
    private int id_transaction;
    private double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian);
        initComponent();
        setData();
    }

    private void setData() {
        baseApiService.listDetailTransaction(id_transaction).enqueue(new Callback<List<DetailTransaction>>() {
            @Override
            public void onResponse(Call<List<DetailTransaction>> call, Response<List<DetailTransaction>> response) {
                if (response.isSuccessful()){
                    detailTransactions = response.body();
                    if (!detailTransactions.isEmpty()){
                        rv_product.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        DetailTransactionAdapter detailTransactionAdapter = new DetailTransactionAdapter(getApplicationContext());
                        detailTransactionAdapter.setDetailTransactions(detailTransactions);
                        rv_product.setAdapter(detailTransactionAdapter);

                        for (int i = 0; i<detailTransactions.size(); i++){
                            price = price + detailTransactions.get(i).getSub_total();
                        }

                        tv_total.setText(Double.toString(price));
                        tv_sub_total.setText(Double.toString(price));
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
        id_transaction = getIntent().getIntExtra("ID_TRANSACTION", 0);
        baseApiService = UtilsApi.getAPIService();
        detailTransactions = new ArrayList<>();

        rv_product = findViewById(R.id.list_product);
        tv_status_pesanan = findViewById(R.id.txt_message);
        tv_status_pengiriman = findViewById(R.id.txt_status_barang);
        tv_address = findViewById(R.id.txt_address);
        tv_total = findViewById(R.id.txt_fulltotal);
        tv_sub_total = findViewById(R.id.txt_subtotal);
    }
}

package com.d4ti.lapoutta.activity.checkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.PembayaranActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.adapter.DetailTransactionAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Address;
import com.d4ti.lapoutta.modal.DetailTransaction;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineActivity extends AppCompatActivity {

    private BaseApiService baseApiService;
    private RecyclerView rv_detailTransaction;
    private Button btn_add, btn_check;
    private EditText et_address, et_provice, et_kabupatenKota;
    private TextView tv_subTotal, tv_total;
    private int id_transaction;
    private int id_customer;
    private double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        initComponent();
        setData();

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PembayaranActivity.class);
                intent.putExtra("ID_TRANSACTION", id_transaction);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveData() {
        baseApiService.createAddress(id_customer, et_address.getText().toString(), et_provice.getText().toString(), et_kabupatenKota.getText().toString()).enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful()){
                    btn_check.setVisibility(View.INVISIBLE);
                    btn_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setData() {
        baseApiService.listDetailTransaction(id_transaction).enqueue(new Callback<List<DetailTransaction>>() {
            @Override
            public void onResponse(Call<List<DetailTransaction>> call, Response<List<DetailTransaction>> response) {
                if (response.isSuccessful()){
                    List<DetailTransaction> detailTransactions = response.body();
                    if (!detailTransactions.isEmpty()){
                        rv_detailTransaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        DetailTransactionAdapter detailTransactionAdapter = new DetailTransactionAdapter(getApplicationContext());
                        detailTransactionAdapter.setDetailTransactions(detailTransactions);
                        rv_detailTransaction.setAdapter(detailTransactionAdapter);

                        for (int i = 0; i<detailTransactions.size(); i++){
                            price = price + detailTransactions.get(i).getSub_total();
                        }

                        tv_subTotal.setText(Double.toString(price));
                        tv_total.setText(Double.toString(price));
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
        id_customer = SaveSharedPreference.getIdUser(this);
        id_transaction = getIntent().getIntExtra("ID_TRANSACTION", 0);
        baseApiService = UtilsApi.getAPIService();
        rv_detailTransaction = findViewById(R.id.list_chart);
        btn_add = findViewById(R.id.add_btn);
        btn_check = findViewById(R.id.btn_check);
        btn_add.setVisibility(View.INVISIBLE);
        et_address = findViewById(R.id.et_address);
        et_provice = findViewById(R.id.et_provinsi);
        et_kabupatenKota = findViewById(R.id.et_kabupatenKota);
        tv_subTotal = findViewById(R.id.txt_pro);
        tv_total = findViewById(R.id.txt_pen);
    }
}

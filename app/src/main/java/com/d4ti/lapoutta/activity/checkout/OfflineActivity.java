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
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.adapter.DetailTransactionAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.DetailTransaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineActivity extends AppCompatActivity {

    private BaseApiService baseApiService;
    private RecyclerView rv_detailTransaction;
    private EditText et_tanggal;
    private EditText et_pukul;
    private EditText et_name;
    private TextView tv_total;
    private Button btn_add;
    private int id_transaction;
    private double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        initComponent();
        setData();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });
    }

    private void saveData() {
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
        id_transaction = getIntent().getIntExtra("ID_TRANSACTION", 0);
        baseApiService = UtilsApi.getAPIService();
        rv_detailTransaction = findViewById(R.id.list_chart);
        et_tanggal = findViewById(R.id.et_tanggal);
        et_pukul = findViewById(R.id.et_pukul);
        et_name = findViewById(R.id.et_name);
        tv_total = findViewById(R.id.txt_ttl);
        btn_add = findViewById(R.id.btn_add);
    }
}

package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.checkout.AngkotActivity;
import com.d4ti.lapoutta.activity.checkout.OfflineActivity;
import com.d4ti.lapoutta.activity.checkout.OnlineActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.activity.store.SplashStoreActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetodeActivity extends AppCompatActivity {

    private BaseApiService baseApiService;
    private Button btnOnline, btnOffline, btnAngkot;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode);

        initComponent();

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseApiService.updateTransactionShipment(id, 2).enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        Intent intent = new Intent(getApplicationContext(), OnlineActivity.class);
                        intent.putExtra("ID_TRANSACTION", id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseApiService.updateTransactionShipment(id, 1).enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        Intent intent = new Intent(getApplicationContext(), OfflineActivity.class);
                        intent.putExtra("ID_TRANSACTION", id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        btnAngkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseApiService.updateTransactionShipment(id, 3).enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        Intent intent = new Intent(getApplicationContext(), AngkotActivity.class);
                        intent.putExtra("ID_TRANSACTION", id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private void initComponent() {
        id = getIntent().getIntExtra("ID_TRANSACTION", 0);
        baseApiService = UtilsApi.getAPIService();
        btnAngkot = findViewById(R.id.btn_angkot);
        btnOffline = findViewById(R.id.btn_offline);
        btnOnline = findViewById(R.id.btn_online);
    }
}

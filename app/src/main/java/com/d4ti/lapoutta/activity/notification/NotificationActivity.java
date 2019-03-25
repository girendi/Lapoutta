package com.d4ti.lapoutta.activity.notification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.adapter.NotifAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Notif;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private TextView txtDataEmpty;
    private RecyclerView rv_notif;
    private BaseApiService baseApiService;

    private List<Notif> notifs;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getLoggedStatus(this) != true){
            Toast.makeText(this, "Status : " + SaveSharedPreference.getLoggedStatus(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = findViewById(R.id.toolbar_notif);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notifikasi");

        initComponent();

        setData();
    }

    private void initComponent() {
        id  = SaveSharedPreference.getIdUser(this);
        baseApiService = UtilsApi.getAPIService();
        notifs = new ArrayList<>();
        txtDataEmpty = findViewById(R.id.data_empty);
        rv_notif = findViewById(R.id.rv_notif);
        rv_notif.setVisibility(View.INVISIBLE);
    }

    private void setData() {
        baseApiService.getListNotif(id).enqueue(new Callback<List<Notif>>() {
            @Override
            public void onResponse(Call<List<Notif>> call, Response<List<Notif>> response) {
                if (response.isSuccessful()){
                   notifs = response.body();
                   if (!notifs.isEmpty()){
                       txtDataEmpty.setVisibility(View.INVISIBLE);
                       rv_notif.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                       NotifAdapter notifAdapter = new NotifAdapter(getApplicationContext());
                       notifAdapter.setNotifs(notifs);
                       rv_notif.setAdapter(notifAdapter);
                   }
                }
            }

            @Override
            public void onFailure(Call<List<Notif>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }
}

package com.d4ti.lapoutta.activity.store;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.fragment.registerStore.InformationFragment;
import com.d4ti.lapoutta.modal.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterStoreActivity extends AppCompatActivity {

    private Button btnInfo, btnNext;
    private TextView txtStatus;
    private int id_store;
    private BaseApiService baseApiService;
    private List<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);
        checkMaps();
        initComponent();
        setData();
    }

    private void checkMaps() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        } else {
            // Permission has already been granted
            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        if (id_store!=0){
            baseApiService.detailStore(id_store).enqueue(new Callback<List<Store>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                    if (response.isSuccessful()){
                        stores = response.body();
                        assert stores != null;
                        if (!stores.isEmpty()){
                            txtStatus.setVisibility(View.VISIBLE);
                            if (stores.get(0).getId() == 1){
                                txtStatus.setText("Status Store Menunggu Konfirmasi");
                            }else if (stores.get(0).getId() == 3){
                                txtStatus.setText("Status Store Ditolak");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Store>> call, Throwable t) {
                    Log.e("Error Message", t.getMessage());
                }
            });
        }
    }

    private void initComponent() {
        id_store = getIntent().getIntExtra("ID_STORE", 0);
        baseApiService = UtilsApi.getAPIService();
        btnInfo = findViewById(R.id.btn_information);
        btnNext = findViewById(R.id.btn_lanjutan);
        txtStatus = findViewById(R.id.txt_status);
        txtStatus.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        InformationFragment informationFragment = new InformationFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_register_store, informationFragment).commit();
    }
}

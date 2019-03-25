package com.d4ti.lapoutta.activity.store;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        initComponent();
        setData();
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

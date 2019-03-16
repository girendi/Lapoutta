package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStoreActivity extends AppCompatActivity {

    private BaseApiService baseApiService;
    private List<Store> storeList;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);

        if (!SaveSharedPreference.getLoggedStatus(this)){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        id  = SaveSharedPreference.getIdUser(this);
        initComponent();
        checkStore();


    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        storeList = new ArrayList<>();
    }

    private void checkStore(){
        baseApiService.detailStore(id).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    storeList = response.body();
                    if (storeList != null){
                        Toast.makeText(MyStoreActivity.this, "Store Telah Terdaftar", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyStoreActivity.this, "Store Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MyStoreActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("Message Error: ", t.getMessage());
            }
        });
    }
}

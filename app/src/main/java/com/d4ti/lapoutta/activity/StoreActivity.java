package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Store;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {

    private CircleImageView imgStore;
    private TextView txtNameStore, txtLocation, txtViewLocation, txtDesc, txtViewStore, txtDataEmpty;
    private RecyclerView rv_product;

    private BaseApiService baseApiService;

    private List<Store> stores;

    private int id_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        id_store = getIntent().getIntExtra("ID_STORE", 0);
        initComponent();
        setStore();

        txtViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LokasiActivity.class));
            }
        });

        txtViewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StoriActivity.class));
            }
        });
    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        imgStore = findViewById(R.id.image_toko);
        txtNameStore = findViewById(R.id.txt_name_store);
        txtLocation = findViewById(R.id.txt_lokasi);
        txtDesc = findViewById(R.id.txt_desc);
        txtViewLocation = findViewById(R.id.txt_view_location);
        txtViewStore = findViewById(R.id.txt_view_stori);
        txtDataEmpty = findViewById(R.id.data_empty);
        rv_product = findViewById(R.id.list_product);
        rv_product.setVisibility(View.INVISIBLE);
    }


    public void setStore(){
        baseApiService.detailStore(id_store).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    stores = response.body();

                    //Store
                    txtNameStore.setText(stores.get(0).getName());
                    txtLocation.setText(stores.get(0).getAddress());
                    txtDesc.setText(stores.get(0).getNo_telp());
                    //Glide.with(getApplicationContext()).load(stores.get(0).getName()).into(imgStore);

                }else {
                    Log.e("Error", "Tidak Dapat Mengambil Data");
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("Error Message" , t.getMessage());
            }
        });
    }
}

package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.activity.store.SplashStoreActivity;
import com.d4ti.lapoutta.adapter.ProductAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {

    private ImageView imgLStore, imgMessage, imgNotif, imgProfile, imgChart, imgHome;
    private CircleImageView imgStore;
    private TextView txtNameStore, txtLocation, txtViewLocation, txtDesc, txtDataEmpty, txtHeader;
    private RecyclerView rv_product;

    private BaseApiService baseApiService;

    private List<Product> products;
    private List<Store> stores;

    private int id_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        id_store = getIntent().getIntExtra("ID_STORE", 0);
        initComponent();
        setStore();
        getProduct();

        imgLStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SplashStoreActivity.class));
            }
        });

        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });

        imgNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        imgChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
            }
        });

        txtViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStori = new Intent(getApplicationContext(), LokasiActivity.class);
                intentStori.putExtra("ID_STORE", id_store);
                startActivity(intentStori);
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
        txtDataEmpty = findViewById(R.id.data_empty);
        txtHeader = findViewById(R.id.txt_header);
        rv_product = findViewById(R.id.list_product);
        rv_product.setVisibility(View.INVISIBLE);

        stores = new ArrayList<>();
        products = new ArrayList<>();

        imgHome = findViewById(R.id.image_home);
        imgLStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
        imgChart = findViewById(R.id.image_troli);
    }


    public void setStore(){
        baseApiService.detailStore(id_store).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    stores = response.body();

                    //Store
                    txtNameStore.setText(stores.get(0).getName());
                    txtHeader.setText(stores.get(0).getName());
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

    public void getProduct(){
        baseApiService.getStoreProduct(id_store).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getProduct();
                    if (!products.isEmpty()){
                        txtDataEmpty.setVisibility(View.INVISIBLE);
                        rv_product.setVisibility(View.VISIBLE);
                        rv_product.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext());
                        productAdapter.setProducts(products);
                        rv_product.setAdapter(productAdapter);
                        Toast.makeText(StoreActivity.this, "Data Ada", Toast.LENGTH_SHORT).show();
                    }else {
                        txtDataEmpty.setVisibility(View.VISIBLE);
                        rv_product.setVisibility(View.INVISIBLE);
                        Toast.makeText(StoreActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    txtDataEmpty.setVisibility(View.VISIBLE);
                    rv_product.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }
}

package com.d4ti.lapoutta.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.activity.store.SplashStoreActivity;
import com.d4ti.lapoutta.adapter.ProductAdapter;
import com.d4ti.lapoutta.adapter.SlideAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Slide;
import com.d4ti.lapoutta.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imgStore, imgMessage, imgNotif, imgProfile;
    private ImageView imgChart;
    private RecyclerView rv_product;
    private BaseApiService baseApiService;
    private AdapterViewFlipper adapterViewFlipper;

    private List<Product> products;
    private List<Slide> slides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        loadSlide();
        getListProduct();

        imgStore.setOnClickListener(new View.OnClickListener() {
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
    }

    public void initComponent(){
        baseApiService = UtilsApi.getAPIService();
        products = new ArrayList<>();
        slides = new ArrayList<>();
        adapterViewFlipper = findViewById(R.id.adapterViewFlipper);
        rv_product = findViewById(R.id.list_product);
        rv_product.setHasFixedSize(true);

        imgStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
        imgChart = findViewById(R.id.image_troli);
    }

    public void loadSlide(){
        baseApiService.getSlide().enqueue(new Callback<List<Slide>>() {
            @Override
            public void onResponse(Call<List<Slide>> call, Response<List<Slide>> response) {
                if (response.isSuccessful()){
                    slides = response.body();
                    if (!slides.isEmpty()){
                        SlideAdapter adapter = new SlideAdapter(getApplicationContext(), slides);
                        adapterViewFlipper.setAdapter(adapter);
                        adapterViewFlipper.setFlipInterval(5000);
                        adapterViewFlipper.startFlipping();
                        Log.i("Sukses", slides.get(0).getName());
                    }
                }else {
                    Log.e("Error", response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Slide>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

    public void getListProduct(){
        baseApiService.getProduct().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getProduct();
                    if (products.size() != 0){
                        rv_product.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext());
                        productAdapter.setProducts(products);
                        rv_product.setAdapter(productAdapter);
                    }else {
                        Log.i("Message", "Produk Belum Ada");
                    }
                }else{
                    Log.e("Error", "Product Tidak Ditemukan");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }
}

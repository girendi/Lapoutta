package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ViewChatActivity;
import com.d4ti.lapoutta.adapter.ProductSlideAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Store;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView imgLogo, imgTroli, imgToko, imgChart, imgMessage;
    private Button btnBuy;
    private TextView txtHeader, txtName, txtPrice, txtDesc, txtToko, txtLokasi, txtNToko;

    private BaseApiService baseApiService;
    private AdapterViewFlipper adapterViewFlipper;

    private RecyclerView rv_review;

    private List<Product> products;
    private List<Store> stores;

    private int id, id_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        id = getIntent().getIntExtra("ID_PRODUCT", 0);

        initComponent();
        txtHeader.setText("Detail Produk");
        setData();

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        imgTroli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
            }
        });
        txtNToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStore = new Intent(getApplicationContext(), StoreActivity.class);
                intentStore.putExtra("ID_STORE", id_store);
                startActivity(intentStore);
            }
        });
        imgChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewChatActivity.class));
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
            }
        });
    }

    public void initComponent(){
        baseApiService = UtilsApi.getAPIService();

        products = new ArrayList<>();
        stores = new ArrayList<>();

        imgLogo = findViewById(R.id.image_home);
        imgTroli = findViewById(R.id.image_troli);
        imgChart = findViewById(R.id.img_chart);
        imgToko = findViewById(R.id.image_toko);
        imgMessage = findViewById(R.id.img_message);

        txtHeader = findViewById(R.id.txt_header);
        txtName = findViewById(R.id.txt_name_product);
        txtPrice = findViewById(R.id.txt_price_product);
        txtDesc = findViewById(R.id.txt_desc_product);
        txtToko = findViewById(R.id.txt_toko);
        txtLokasi = findViewById(R.id.txt_lokasi);
        txtNToko = findViewById(R.id.txt_ntoko);

        btnBuy = findViewById(R.id.btn_buy);

        rv_review = findViewById(R.id.rv_review);

        adapterViewFlipper = findViewById(R.id.adapterViewFlipper);
    }

    public void setData(){
        baseApiService.detailProduct(id).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    products = response.body();

                    //Set Data Product
                    txtName.setText(products.get(0).getName());
                    txtPrice.setText(Double.toString(products.get(0).getPrice()));
                    txtDesc.setText(products.get(0).getDescription());
                    id_store = products.get(0).getStore().getId();

                    //Slider Product
                    ProductSlideAdapter adapter = new ProductSlideAdapter(getApplicationContext(), products);
                    adapterViewFlipper.setAdapter(adapter);
                    adapterViewFlipper.setFlipInterval(1000);
                    adapterViewFlipper.startFlipping();

                    //Set Data Store
                    txtToko.setText(products.get(0).getStore().getName());
                    txtLokasi.setText(products.get(0).getStore().getAddress());
                    //Glide.with(getApplicationContext()).load(products.get(0).getStore().getCustomer().getImage()).into(imgToko);

                }else {
                    Log.e("Error", "Tidak Dapat Mengambil Data");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error Message" , t.getMessage());
            }
        });
    }

    public void setStore(){
        baseApiService.detailStore(id_store).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    stores = response.body();

                    //Store
                    txtToko.setText(stores.get(0).getName());
                    txtLokasi.setText(stores.get(0).getAddress());
                    Glide.with(getApplicationContext()).load(stores.get(0).getName()).into(imgToko);

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

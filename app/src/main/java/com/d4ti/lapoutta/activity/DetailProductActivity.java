package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.d4ti.lapoutta.activity.review.ReviewActivity;
import com.d4ti.lapoutta.adapter.ProductSlideAdapter;
import com.d4ti.lapoutta.adapter.ReviewAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Chart;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Review;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView imgLogo, imgTroli, imgToko, imgChart, imgMessage;
    private Button btnBuy;
    private TextView txtHeader, txtName, txtPrice, txtDesc, txtToko, txtLokasi, txtNToko, txtNext;

    private BaseApiService baseApiService;
    private AdapterViewFlipper adapterViewFlipper;

    private RecyclerView rv_review;

    private List<Product> products;
    private List<Review> reviews;

    private int id_product, id_store, id_customer, id_current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        id_product = getIntent().getIntExtra("ID_PRODUCT", 0);

        initComponent();
        txtHeader.setText("Detail Produk");
        setData();
        setReview();

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        imgTroli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                finish();
            }
        });
        txtNToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStore = new Intent(getApplicationContext(), StoreActivity.class);
                intentStore.putExtra("ID_STORE", id_store);
                startActivity(intentStore);
                finish();
            }
        });
        imgChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToChart();
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStore = new Intent(getApplicationContext(), ViewChatActivity.class);
                intentStore.putExtra("ID_CUSTOMER", id_customer);
                startActivity(intentStore);
                finish();
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToChart();
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                finish();
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("ID_PRODUCT", id_product);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initComponent(){
        baseApiService = UtilsApi.getAPIService();

        id_current_user = SaveSharedPreference.getIdUser(this);

        products = new ArrayList<>();
        reviews = new ArrayList<>();

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
        txtNext = findViewById(R.id.txt_next);

        btnBuy = findViewById(R.id.btn_buy);

        rv_review = findViewById(R.id.rv_review);

        adapterViewFlipper = findViewById(R.id.adapterViewFlipper);
    }

    private void setReview() {
        baseApiService.getListReview(id_product).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()){
                    reviews = response.body();
                    if (!reviews.isEmpty()){
                        rv_review.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ReviewAdapter reviewAdapter = new ReviewAdapter(getApplicationContext());
                        reviewAdapter.setReviews(reviews);
                        rv_review.setAdapter(reviewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

    public void setData(){
        baseApiService.detailProduct(id_product).enqueue(new Callback<List<Product>>() {
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
                    adapterViewFlipper.setFlipInterval(5000);
                    adapterViewFlipper.startFlipping();

                    //Set Data Store
                    txtToko.setText(products.get(0).getStore().getName());
                    txtLokasi.setText(products.get(0).getStore().getAddress());
                    id_customer = products.get(0).getStore().getId_customer();
                    baseApiService.detailCustomer(id_customer).enqueue(new Callback<List<Customer>>() {
                        @Override
                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            if (response.isSuccessful()){
                                List<Customer> customers = response.body();
                                if (!customers.isEmpty()){
                                    Glide.with(getApplicationContext()).load(customers.get(0).getImage()).into(imgToko);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Customer>> call, Throwable t) {
                            Log.e("Error Message", t.getMessage());
                        }
                    });


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

    private void addToChart() {
        baseApiService.createChart(1, true, id_current_user, id_product).enqueue(new Callback<Chart>() {
            @Override
            public void onResponse(Call<Chart> call, Response<Chart> response) {
                if (response.isSuccessful()){
                    Chart chart = response.body();
                }
            }

            @Override
            public void onFailure(Call<Chart> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

}

package com.d4ti.lapoutta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.ProductSlideAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView imgLogo, imgTroli;
    private TextView txtHeader, txtName, txtPrice, txtDesc;

    private BaseApiService baseApiService;
    private AdapterViewFlipper adapterViewFlipper;

    private RecyclerView rv_review;

    private List<Product> products;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        id = getIntent().getIntExtra("ID_PRODUCT", 0);

        initComponent();
        txtHeader.setText("Detail Produk");
        setData();
    }

    public void initComponent(){
        baseApiService = UtilsApi.getAPIService();

        products = new ArrayList<>();

        imgLogo = findViewById(R.id.image_home);
        imgTroli = findViewById(R.id.image_troli);
        txtHeader = findViewById(R.id.txt_header);
        txtName = findViewById(R.id.txt_name_product);
        txtPrice = findViewById(R.id.txt_price_product);
        txtDesc = findViewById(R.id.txt_desc_product);
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

                    //Slider Product
                    ProductSlideAdapter adapter = new ProductSlideAdapter(getApplicationContext(), products);
                    adapterViewFlipper.setAdapter(adapter);
                    adapterViewFlipper.setFlipInterval(1000);
                    adapterViewFlipper.startFlipping();
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

}

package com.d4ti.lapoutta.activity.review;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewActivity extends AppCompatActivity {

    private int id_product, id_customer;
    private TextView txtName;
    private EditText etReview;
    private Button btnSend;
    private BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        initComponent();
        setData();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void initComponent() {
        id_customer = SaveSharedPreference.getIdUser(this);
        id_product = getIntent().getIntExtra("ID_PRODUCT", 0);
        baseApiService = UtilsApi.getAPIService();
        txtName = findViewById(R.id.txt_name);
        etReview = findViewById(R.id.et_review);
        btnSend = findViewById(R.id.btn_send);
    }

    private void setData() {
        baseApiService.detailProduct(id_product).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products;
                if (response.isSuccessful()){
                    products = response.body();
                    if (!products.isEmpty()){
                        txtName.setText(products.get(0).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

    private void saveData() {
        baseApiService.createReview(id_customer, id_product, etReview.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
    }
}
package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
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

public class ChangeProductActivity extends AppCompatActivity {

    private ImageView imgStore, imgMessage, imgNotif, imgProfile, imgProduct;
    private TextView tvHeader;
    private EditText etNameProduct, etPriceProduct, etStockProduct, etProductDesc;
    private Button btnAddImage, btnSave, btnStory;
    private BaseApiService baseApiService;

    private int id_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_product);

        if (SaveSharedPreference.getLoggedStatus(this)!=true){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }else {
            initComponent();
            setData();
            tvHeader.setText("Toko Saya");

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

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });

            btnStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddStorieActivity.class);
                    intent.putExtra("ID_PRODUCT", id_product);
                    startActivity(intent);
                }
            });
        }
    }

    private void setData() {
        baseApiService.detailProduct(id_product).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    List<Product> products = response.body();
                    if (!products.isEmpty()){
                        etNameProduct.setText(products.get(0).getName());
                        etPriceProduct.setText(String.valueOf(products.get(0).getPrice()));
                        etStockProduct.setText(String.valueOf(products.get(0).getStock()));
                        etProductDesc.setText(products.get(0).getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        id_product = getIntent().getIntExtra("ID_PRODUCT", 0);
        baseApiService = UtilsApi.getAPIService();

        tvHeader = findViewById(R.id.txt_header);

        imgStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
        imgProduct = findViewById(R.id.img_product);

        etNameProduct = findViewById(R.id.et_product_name);
        etPriceProduct = findViewById(R.id.et_product_price);
        etStockProduct = findViewById(R.id.et_product_stock);
        etProductDesc = findViewById(R.id.et_product_desc);

        btnAddImage = findViewById(R.id.btn_add_img);
        btnSave = findViewById(R.id.btn_add_product);
        btnStory = findViewById(R.id.btn_add_story);
    }

    private void saveData() {
        baseApiService.updateProduct(id_product, etNameProduct.getText().toString(), Double.parseDouble(etPriceProduct.getText().toString()),
                Integer.parseInt(etStockProduct.getText().toString()), etProductDesc.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ChangeProductActivity.this, "Product Update", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyStoreActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}

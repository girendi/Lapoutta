package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private ImageView imgStore, imgMessage, imgNotif, imgProfile, imgProduct;
    private TextView tvHeader;
    private EditText etNameProduct, etPriceProduct, etStockProduct, etProductDesc;
    private Button btnAddImage, btnSave;
    private BaseApiService baseApiService;

    private int id_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initComponent();
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
    }

    private void saveData() {
        baseApiService.createProduct(etNameProduct.getText().toString(),Double.parseDouble(etPriceProduct.getText().toString()), Integer.parseInt(etStockProduct.getText().toString()),
                etProductDesc.getText().toString(), 1, 1, id_store, 1)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
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

    private void initComponent() {
        id_store = SaveSharedPreference.getIdStore(this);
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

    }
}

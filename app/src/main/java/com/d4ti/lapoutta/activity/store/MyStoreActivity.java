package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.activity.MainActivity;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.fragment.myStore.ProductFragment;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStoreActivity extends AppCompatActivity {

    String url="http://192.168.43.157:1337/images/uploads/";
    private ImageView imgStore, imgMessage, imgNotif, imgProfile, imgHome;
    private CircleImageView img_Store;
    private TextView tvHeader, tvNameStore, tvLocation;
    private Button btnChange, btnAdd;
    private BaseApiService baseApiService;
    private List<Store> storeList;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);

        if (SaveSharedPreference.getLoggedStatus(this)!=true){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        id  = SaveSharedPreference.getIdUser(this);
        initComponent();
        tvHeader.setText("Toko Saya");
        checkStore();

        imgStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SplashStoreActivity.class));
                finish();
            }
        });

        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                finish();
            }
        });

        imgNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangeStoreActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
                finish();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        storeList = new ArrayList<>();

        tvHeader = findViewById(R.id.txt_header);
        tvNameStore = findViewById(R.id.txt_name_store);
        tvLocation = findViewById(R.id.txt_lokasi);

        btnChange = findViewById(R.id.btn_change_store);
        btnAdd = findViewById(R.id.btn_add_product);

        imgStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
        imgHome = findViewById(R.id.image_home);
        img_Store = findViewById(R.id.image_toko);
    }

    private void checkStore(){
        baseApiService.detailStore2(id).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    storeList = response.body();
                    if (storeList.size() != 0){
                        tvNameStore.setText(storeList.get(0).getName());
                        tvLocation.setText(storeList.get(0).getAddress());
                        baseApiService.detailCustomer(storeList.get(0).getId_customer()).enqueue(new Callback<List<Customer>>() {
                            @Override
                            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                                if (response.isSuccessful()){
                                    List<Customer> customers = response.body();
                                    if (!customers.isEmpty()){
                                        Glide.with(getApplicationContext()).load(url + customers.get(0).getImage()).into(img_Store);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Customer>> call, Throwable t) {
                                Log.e("Error Message", t.getMessage());
                            }
                        });
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

    @Override
    protected void onStart() {
        super.onStart();
        ProductFragment productFragment = new ProductFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_my_store, productFragment).commit();
    }
}

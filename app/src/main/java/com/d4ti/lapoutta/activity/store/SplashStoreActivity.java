package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashStoreActivity extends AppCompatActivity {

    private ImageView imgStore, imgMessage, imgNotif, imgProfile;

    private Button btn_register;

    private BaseApiService baseApiService;
    private List<Store> storeList;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_store);

        if (SaveSharedPreference.getLoggedStatus(this)!=true){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }else{
            id  = SaveSharedPreference.getIdUser(this);
            initComponent();
            checkStore();

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

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), RegisterStoreActivity.class));
                }
            });
        }
    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        storeList = new ArrayList<>();
        btn_register = findViewById(R.id.btn_register_store);
        btn_register.setVisibility(View.INVISIBLE);

        imgStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
    }

    private void checkStore(){
        baseApiService.detailStore2(id).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    storeList = response.body();
                    if (storeList.isEmpty()){
                        SaveSharedPreference.setIdStore(SplashStoreActivity.this, 0);
                        Toast.makeText(SplashStoreActivity.this, "Store Belum Terdaftar", Toast.LENGTH_SHORT).show();
                        btn_register.setVisibility(View.VISIBLE);
                    } else{
                        SaveSharedPreference.setIdStore(SplashStoreActivity.this, storeList.get(0).getId());
                        if (storeList.get(0).getId_store_status() == 1){
                            btn_register.setVisibility(View.INVISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MyStoreActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), RegisterStoreActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }else {
                    Toast.makeText(SplashStoreActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                    btn_register.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("Message Error: ", t.getMessage());
            }
        });
    }
}

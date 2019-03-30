package com.d4ti.lapoutta.activity.profile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {

    private BaseApiService baseApiService;
    private int id;

    private CircleImageView imgProfile;
    private EditText et_name;
    private EditText et_telepon;
    private Button btn_save;

    private List<Customer> listCustomer;

    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        Toolbar toolbar = findViewById(R.id.toolbar_change_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.profil_saya);

        if (!SaveSharedPreference.getLoggedStatus(this)){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }

        id  = SaveSharedPreference.getIdUser(this);

        initComponent();
        setData();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void setRequestImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        listCustomer = new ArrayList<>();
        imgProfile = findViewById(R.id.img_profile);
        et_name = findViewById(R.id.et_name);
        et_telepon = findViewById(R.id.et_telepon);
        btn_save = findViewById(R.id.btn_simpan);
    }

    private void setData(){
        baseApiService.detailCustomer(id).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()){
                    listCustomer = response.body();
                    if (!listCustomer.isEmpty()){
                        et_name.setText(listCustomer.get(0).getName());
                        et_telepon.setText(listCustomer.get(0).getNo_telp());
                        if (!listCustomer.get(0).getImage().isEmpty()){
                            Glide.with(ChangeProfileActivity.this)
                                    .load(listCustomer.get(0).getImage())
                                    .into(imgProfile);
                        }
                    }
                }else{
                    Log.e("Gagal", "Gagal");
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void saveData(){

        baseApiService.updateProfile(id, et_name.getText().toString(), et_telepon.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            finish();
                        }else {
                            Toast.makeText(ChangeProfileActivity.this, "Gagal Mengubah Profile", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Message: ", t.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uriImage = result.getUri();
                Glide.with(ChangeProfileActivity.this)
                        .load(uriImage)
                        .into(imgProfile);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Error Message ", error.getMessage());
            }
        }

    }

}

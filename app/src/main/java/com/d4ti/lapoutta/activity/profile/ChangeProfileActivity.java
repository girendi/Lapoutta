package com.d4ti.lapoutta.activity.profile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    String url="http://192.168.43.157:1337/images/uploads/";

    private BaseApiService baseApiService;
    private int id;
    String mediaPath;
    private static final int PERMISSION_STORAGE = 2;

    private CircleImageView imgProfile;
    private EditText et_name;
    private EditText et_telepon;
    private Button btn_save, btn_logout;

    private List<Customer> listCustomer;

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
                if (ContextCompat.checkSelfPermission(ChangeProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(ChangeProfileActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ChangeProfileActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);

                }else{
                    setRequestImage();
                }

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                SaveSharedPreference.setIdStore(getApplicationContext(), 0);
                SaveSharedPreference.setEmailUser(getApplicationContext(), null);
                SaveSharedPreference.setIdUser(getApplicationContext(), 0);
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                finish();
            }
        });
    }

    private void setRequestImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        listCustomer = new ArrayList<>();
        imgProfile = findViewById(R.id.img_profile);
        et_name = findViewById(R.id.et_name);
        et_telepon = findViewById(R.id.et_telepon);
        btn_save = findViewById(R.id.btn_simpan);
        btn_logout = findViewById(R.id.btn_logout);
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
                                    .load(url + listCustomer.get(0).getImage())
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
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                uploadImage();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage() {
        File file = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("", file.getName(), requestBody);

        baseApiService.updateImage(id, fileToUpload).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ChangeProfileActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}

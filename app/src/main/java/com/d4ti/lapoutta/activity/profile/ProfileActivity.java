package com.d4ti.lapoutta.activity.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgSetting;
    private ImageView imgMessage;
    private CircleImageView imgProfile;
    private TextView txtName;
    private TextView txtEmail;
    private Button btnVerifikasi;
    private Button btnDikemas;
    private Button btnDikirim;
    private Button btnSelesai;
    private Button btnBatal;
    private BaseApiService baseApiService;
    private List<Customer> customerList;

    private int id;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getLoggedStatus(this) != true){
            Toast.makeText(this, "Status : " + SaveSharedPreference.getLoggedStatus(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        setContentView(R.layout.activity_profile);

        initComponent();

        id  = SaveSharedPreference.getIdUser(this);
        email = SaveSharedPreference.getEmail(this);
        getProfile();

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangeProfileActivity.class));
            }
        });

    }

    public void initComponent(){
        baseApiService = UtilsApi.getAPIService();
        customerList = new ArrayList<>();
        imgSetting = findViewById(R.id.img_setting);
        imgMessage = findViewById(R.id.img_message);
        imgProfile = findViewById(R.id.img_profile);
        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        btnVerifikasi = findViewById(R.id.btn_verifikasi);
        btnDikemas = findViewById(R.id.btn_dikemas);
        btnDikirim = findViewById(R.id.btn_dikirim);
        btnSelesai = findViewById(R.id.btn_selesai);
        btnBatal = findViewById(R.id.btn_batal);
    }

    public void getProfile() {
        baseApiService.detailCustomer(id).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    customerList = response.body();
                    assert customerList != null;
                    txtName.setText(customerList.get(0).getName());
                    txtEmail.setText(email);

                } else {
                    Log.e("Gagal", "Gagal");
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}

package com.d4ti.lapoutta.activity.profile;

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
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.fragment.profile.CancelFragment;
import com.d4ti.lapoutta.fragment.profile.DoneFragment;
import com.d4ti.lapoutta.fragment.profile.NotVerifiedFragment;
import com.d4ti.lapoutta.fragment.profile.PackedFragment;
import com.d4ti.lapoutta.fragment.profile.SentFragment;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    String url="http://192.168.43.157:1337/images/uploads/";

    private ImageView imgHome;
    private ImageView imgMessage;
    private CircleImageView imgProfile;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtSetting;
    private Button btnVerifikasi;
    private Button btnDikemas;
    private Button btnDikirim;
    private Button btnSelesai;
    private Button btnBatal;
    private BaseApiService baseApiService;
    private List<Customer> customerList;

    private NotVerifiedFragment notVerifiedFragment;
    private PackedFragment packedFragment;
    private SentFragment sentFragment;
    private DoneFragment doneFragment;
    private CancelFragment cancelFragment;

    private FragmentManager manager;

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
        setFragment();

        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangeProfileActivity.class));
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });

        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.frameLayout, notVerifiedFragment).commit();
            }
        });

        btnDikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.frameLayout, packedFragment).commit();
            }
        });

        btnDikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.frameLayout, sentFragment).commit();
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.frameLayout, doneFragment).commit();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.frameLayout, cancelFragment).commit();
            }
        });

    }

    private void setFragment() {
        manager.beginTransaction().replace(R.id.frameLayout, notVerifiedFragment).commit();
    }

    public void initComponent(){
        //api
        baseApiService = UtilsApi.getAPIService();

        //list model
        customerList = new ArrayList<>();

        //view
        txtSetting = findViewById(R.id.txt_setting);
        imgMessage = findViewById(R.id.img_message);
        imgProfile = findViewById(R.id.img_profile);
        imgHome = findViewById(R.id.img_home);
        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        btnVerifikasi = findViewById(R.id.btn_verifikasi);
        btnDikemas = findViewById(R.id.btn_dikemas);
        btnDikirim = findViewById(R.id.btn_dikirim);
        btnSelesai = findViewById(R.id.btn_selesai);
        btnBatal = findViewById(R.id.btn_batal);

        //fragment
        notVerifiedFragment = new NotVerifiedFragment();
        packedFragment = new PackedFragment();
        sentFragment = new SentFragment();
        doneFragment = new DoneFragment();
        cancelFragment = new CancelFragment();
        manager = getSupportFragmentManager();
    }

    public void getProfile() {
        baseApiService.detailCustomer(id).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    customerList = response.body();
                    if (!customerList.isEmpty()){
                        txtName.setText(customerList.get(0).getName());
                        txtEmail.setText(customerList.get(0).getUser().getEmail());
                        if (!customerList.get(0).getImage().isEmpty()){
                            Glide.with(ProfileActivity.this)
                                    .load(url + customerList.get(0).getImage())
                                    .into(imgProfile);
                        }
                    }

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

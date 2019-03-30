package com.d4ti.lapoutta.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.chat.ChatActivity;
import com.d4ti.lapoutta.activity.notification.NotificationActivity;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeStoreActivity extends AppCompatActivity {

    private ImageView imgStore, imgMessage, imgNotif, imgProfile;
    private TextView tvHeader;
    private EditText etNameStore, etNoTelp, etNoKTP, etNoRek;
    private TextView tvLocation;
    private Button btnUpdate;
    private BaseApiService baseApiService;
    private int id_customer;
    private String longitude = null;
    LatLng latlong;
    private List<Store> stores;
    private final static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_store);
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPlaceAutocompleteActivityIntent();
            }
        });
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.        }

        }
    }

    private void setData() {
        baseApiService.detailStore2(id_customer).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    stores = response.body();
                    if (!stores.isEmpty()){
                        etNameStore.setText(stores.get(0).getName());
                        etNoKTP.setText(stores.get(0).getNo_KTP());
                        etNoTelp.setText(stores.get(0).getNo_telp());
                        etNoRek.setText(stores.get(0).getNo_Rekening());
                        tvLocation.setText(stores.get(0).getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateData() {
        baseApiService.updateStore(id_customer, etNameStore.getText().toString(), etNoTelp.getText().toString(), tvLocation.getText().toString(),
                etNoKTP.getText().toString(), etNoRek.getText().toString(), longitude).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                Toast.makeText(ChangeStoreActivity.this, "Toko telah diupdate", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        baseApiService = UtilsApi.getAPIService();
        id_customer = SaveSharedPreference.getIdUser(this);
        stores = new ArrayList<>();

        tvHeader = findViewById(R.id.txt_header);

        etNameStore = findViewById(R.id.et_name_store);
        etNoTelp = findViewById(R.id.et_telp_store);
        etNoKTP = findViewById(R.id.et_nomor_ktp);
        etNoRek = findViewById(R.id.et_no_rek);
        tvLocation = findViewById(R.id.tvLocation);
        btnUpdate = findViewById(R.id.btn_update_store);

        imgStore = findViewById(R.id.img_store);
        imgMessage = findViewById(R.id.img_message);
        imgNotif = findViewById(R.id.img_notif);
        imgProfile = findViewById(R.id.img_profile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                latlong = place.getLatLng();
                tvLocation.setText(place.getAddress().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (requestCode == RESULT_CANCELED) {
                Log.i("Result Canceled", "true");
            }
        }
    }
}

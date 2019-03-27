package com.d4ti.lapoutta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.network.ApiServicesMaps;
import com.d4ti.lapoutta.apiHelper.network.InitLibraryMaps;
import com.d4ti.lapoutta.modal.maps.ResponseRoute;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import retrofit2.Call;

public class LokasiActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private String API_KEY = "your_api_key";
    private TextView tvPrice, tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        Toolbar toolbar = findViewById(R.id.toolbar_lokasi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Lokasi Toko");

        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setView();

        actionRoute();
    }

    private void actionRoute() {
        ApiServicesMaps apiServicesMaps = InitLibraryMaps.getInstance();
        Call<ResponseRoute> responseBodyCall = apiServicesMaps.request_route(tvPrice.getText().toString(), tvPrice.getText().toString(), API_KEY);
    }

    private void setView(){
        tvPrice = findViewById(R.id.tvPrice);
        tvDistance = findViewById(R.id.tvDistance);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

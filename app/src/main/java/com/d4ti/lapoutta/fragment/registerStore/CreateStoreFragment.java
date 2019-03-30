package com.d4ti.lapoutta.fragment.registerStore;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.MainActivity;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateStoreFragment extends Fragment {

    private View view;
    private EditText etNameStore, etNoTelp, etNoKTP, etNoRek;
    private TextView tvLocation;
    private String latlang;
    private Button btnSave;
    LatLng latlong;

    private BaseApiService baseApiService;

    private final static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 12;

    private int id_store, id_customer;

    public CreateStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_store, container, false);
        setView();
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPlaceAutocompleteActivityIntent();
            }
        });

        if (id_store!=0){
            setData();
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStore();
                }
            });
        }else{
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveStore();
                }
            });
        }


        return view;
    }

    private void setData() {
        baseApiService.detailStore2(id_customer).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    List<Store> stores = response.body();
                    if (!stores.isEmpty()){
                        etNameStore.setText(stores.get(0).getName());
                        etNoTelp.setText(stores.get(0).getNo_telp());
                        etNoKTP.setText(stores.get(0).getNo_KTP());
                        etNoRek.setText(stores.get(0).getNo_Rekening());
                        tvLocation.setText(stores.get(0).getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

    private void saveStore() {
        baseApiService.createStore(etNameStore.getText().toString(), etNoTelp.getText().toString(), tvLocation.getText().toString(),
                etNoKTP.getText().toString(), etNoRek.getText().toString(), latlang, id_customer)
                .enqueue(new Callback<Store>() {
                    @Override
                    public void onResponse(Call<Store> call, Response<Store> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Toko Telah Dibuat", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Store> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
    }

    private void updateStore() {
        baseApiService.updateStore(id_customer, etNameStore.getText().toString(), etNoTelp.getText().toString(), tvLocation.getText().toString(),
                etNoKTP.getText().toString(), etNoRek.getText().toString(), latlang)
                .enqueue(new Callback<List<Store>>() {
                    @Override
                    public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Toko Telah Diubah", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Store>> call, Throwable t) {
                        Log.e("Error Message", t.getMessage());
                    }
                });
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.        }

        }
    }

    private void setView(){
        id_store = SaveSharedPreference.getIdStore(getActivity());
        id_customer = SaveSharedPreference.getIdUser(getActivity());
        baseApiService = UtilsApi.getAPIService();
        etNameStore = view.findViewById(R.id.et_name_store);
        etNoTelp = view.findViewById(R.id.et_telp_store);
        etNoKTP = view.findViewById(R.id.et_nomor_ktp);
        etNoRek = view.findViewById(R.id.et_no_rek);
        tvLocation = view.findViewById(R.id.tvLocation);
        btnSave = view.findViewById(R.id.btn_save_store);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                    latlong = place.getLatLng();
                    tvLocation.setText(place.getAddress().toString());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                } else if (requestCode == RESULT_CANCELED) {
                    Log.i("Result Canceled", "true");
                }
            }
    }
}

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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateStoreFragment extends Fragment {

    private View view;
    private EditText etNameStore, etNoTelp, etNoKTP, etNoNPWP, etDesc;
    private TextView tvLocation;
    private String latlang;
    private Button btnSave;

    private boolean status;

    public static final int PICK_UP = 0;
    private static int REQUEST_CODE = 0;

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
                showPlaceAutoComplete(PICK_UP);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void showPlaceAutoComplete(int pickUp) {
        REQUEST_CODE = pickUp;
        AutocompleteFilter filter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            Intent mIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(filter)
                    .build(getActivity());
            startActivityForResult(mIntent, REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(){
        etNameStore = view.findViewById(R.id.et_name_store);
        etNoTelp = view.findViewById(R.id.et_telp_store);
        etNoKTP = view.findViewById(R.id.et_nomor_ktp);
        etNoNPWP = view.findViewById(R.id.et_npwp);
        etDesc = view.findViewById(R.id.et_desc);
        tvLocation = view.findViewById(R.id.tv_location);
        btnSave = view.findViewById(R.id.btn_save_store);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Place placeData = PlaceAutocomplete.getPlace(getActivity(), data);
            if (placeData.isDataValid()){
                Log.d("autoCompletePlace Data", placeData.toString());
                String placeAddress = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
//                String placeName = placeData.getName().toString();
                switch (REQUEST_CODE){
                    case PICK_UP:
                        tvLocation.setText(placeAddress);
                        latlang = placeLatLng.latitude + "," + placeLatLng.longitude;
                        break;
                }
            }else {
                // Data tempat tidak valid
                Toast.makeText(getActivity(), "Invalid Place !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

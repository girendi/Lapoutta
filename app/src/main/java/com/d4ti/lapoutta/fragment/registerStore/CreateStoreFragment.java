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
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.MainActivity;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateStoreFragment extends Fragment {

    private View view;
    private EditText etNameStore, etNoTelp, etNoKTP, etNoRek, etAddress, etProvinsi, etKabupatenKota;
    private String location;
    private Button btnSave;

    private BaseApiService baseApiService;

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
                        etAddress.setText(stores.get(0).getAddress());
                        etProvinsi.setText(stores.get(0).getProvinsi());
                        etKabupatenKota.setText(stores.get(0).getKabupatenKota());
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
        baseApiService.createStore(etNameStore.getText().toString(), etNoTelp.getText().toString(), etAddress.getText().toString(),
                etProvinsi.getText().toString(), etKabupatenKota.getText().toString(),etNoKTP.getText().toString(), etNoRek.getText().toString(), id_customer)
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
        baseApiService.updateStore(id_customer, etNameStore.getText().toString(), etNoTelp.getText().toString(), etAddress.getText().toString(),
                etProvinsi.getText().toString(), etKabupatenKota.getText().toString(),
                etNoKTP.getText().toString(), etNoRek.getText().toString())
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

    private void setView(){

        id_store = SaveSharedPreference.getIdStore(getActivity());
        id_customer = SaveSharedPreference.getIdUser(getActivity());
        baseApiService = UtilsApi.getAPIService();
        etNameStore = view.findViewById(R.id.et_name_store);
        etNoTelp = view.findViewById(R.id.et_telp_store);
        etNoKTP = view.findViewById(R.id.et_nomor_ktp);
        etNoRek = view.findViewById(R.id.et_no_rek);
        btnSave = view.findViewById(R.id.btn_save_store);
        etAddress = view.findViewById(R.id.et_location);
        etProvinsi = view.findViewById(R.id.et_provinsi);
        etKabupatenKota = view.findViewById(R.id.et_kabupatenKota);
    }
}

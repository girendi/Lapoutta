package com.d4ti.lapoutta.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText et_name, et_email, et_pass, et_confir;
    private Button btn_register;
    private View view;
    private BaseApiService baseApiService;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        baseApiService = UtilsApi.getAPIService();

        initComponent();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                //goToLogin();
            }
        });

        return view;
    }

    private void register() {
        baseApiService.registerRequest(et_name.getText().toString(), et_email.getText().toString(), et_pass.getText().toString(), et_confir.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            goToLogin();
                        }else{
                            Toast.makeText(getActivity(), "Register Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Register Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToLogin() {
        Fragment fragment = new LoginFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void initComponent() {
        et_name = view.findViewById(R.id.txt_name);
        et_email = view.findViewById(R.id.txt_email);
        et_pass = view.findViewById(R.id.txt_password);
        et_confir = view.findViewById(R.id.txt_confir_password);
        btn_register = view.findViewById(R.id.btn_register);
    }



}

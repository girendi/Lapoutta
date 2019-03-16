package com.d4ti.lapoutta.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.d4ti.lapoutta.activity.MainActivity;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private Button btn_login;
    private EditText et_username, et_password;
    private View view;
    private BaseApiService baseApiService;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (SaveSharedPreference.getLoggedStatus(getActivity())){
            startActivity(new Intent(getActivity(), MainActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
        view = inflater.inflate(R.layout.fragment_login, container, false);
        baseApiService = UtilsApi.getAPIService();
        initComponent();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void initComponent() {
        et_username = view.findViewById(R.id.txt_email);
        et_password = view.findViewById(R.id.txt_password);
        btn_login = view.findViewById(R.id.btn_login);
    }

    private void login(){
        baseApiService.loginRequest(et_username.getText().toString(), et_password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("status").equals("success")){
                                    int id = jsonObject.getJSONObject("data").getInt("id");
                                    String email = jsonObject.getJSONObject("data").getString("email");
                                    SaveSharedPreference.setLoggedIn(getActivity(), true);
                                    SaveSharedPreference.setIdUser(getActivity(), id);
                                    SaveSharedPreference.setEmailUser(getActivity(), email);
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

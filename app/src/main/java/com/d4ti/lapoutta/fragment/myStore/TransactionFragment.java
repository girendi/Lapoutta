package com.d4ti.lapoutta.fragment.myStore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    private TextView data_empty;
    private RecyclerView rv_transaction;
    private BaseApiService baseApiService;
    private View view;
    private int id_customer;

    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction, container, false);
        initComponent();
        setData();
        return view;
    }

    private void setData() {

    }

    private void initComponent() {
        id_customer = SaveSharedPreference.getIdUser(getContext());
        baseApiService = UtilsApi.getAPIService();
        data_empty = view.findViewById(R.id.data_empty);
        rv_transaction = view.findViewById(R.id.rv_transaction);
    }

}

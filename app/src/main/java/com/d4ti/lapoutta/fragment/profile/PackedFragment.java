package com.d4ti.lapoutta.fragment.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.TransactionUserAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Transaction;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackedFragment extends Fragment {

    private View view;
    private TextView txtDataEmpty;
    private RecyclerView rv_transaction;
    private BaseApiService baseApiService;
    private int id_customer;

    public PackedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_packed, container, false);
        // Inflate the layout for this fragment
        initComponent();
        dataSet();
        return view;
    }

    private void dataSet() {
        baseApiService.listTransactionUser(id_customer).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()){
                    List<Transaction> transactions = response.body();
                    if (!transactions.isEmpty()){

                        List<Transaction> trans = new ArrayList<>();

                        for (int i = 0; i<transactions.size(); i++){
                            if (transactions.get(i).getId_transaction_status() == 2 || transactions.get(i).getId_transaction_status() == 3){
                                trans.add(transactions.get(i));
                            }
                        }

                        txtDataEmpty.setVisibility(View.INVISIBLE);
                        rv_transaction.setVisibility(View.VISIBLE);
                        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext()));
                        TransactionUserAdapter transactionUserAdapter = new TransactionUserAdapter(getActivity());
                        transactionUserAdapter.setTransactions(trans);
                        rv_transaction.setAdapter(transactionUserAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        id_customer = SaveSharedPreference.getIdUser(getContext());
        baseApiService = UtilsApi.getAPIService();
        txtDataEmpty = view.findViewById(R.id.data_empty);
        rv_transaction = view.findViewById(R.id.rv_transaksi);
        rv_transaction.setVisibility(View.INVISIBLE);
    }
}

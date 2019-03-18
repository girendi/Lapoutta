package com.d4ti.lapoutta.fragment.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d4ti.lapoutta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentFragment extends Fragment {

    private View view;
    private TextView txtDataEmpty;
    private RecyclerView rv_transaction;

    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sent, container, false);
        // Inflate the layout for this fragment
        initComponent();
        return view;
    }

    private void initComponent() {
        txtDataEmpty = view.findViewById(R.id.data_empty);
        rv_transaction = view.findViewById(R.id.rv_transaksi);
        rv_transaction.setVisibility(View.INVISIBLE);
    }


}

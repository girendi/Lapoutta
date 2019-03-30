package com.d4ti.lapoutta.fragment.registerStore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.d4ti.lapoutta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    private Button btn_next;
    private CheckBox cb_agree;
    private View view;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_information, container, false);
        initComponent();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_agree.isChecked()){
                    goToRegister();
                }else {
                    Toast.makeText(getActivity(), "Please Check the CheckBox", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void initComponent() {
        btn_next = view.findViewById(R.id.btn_agree);
        cb_agree = view.findViewById(R.id.cb_information);
    }

    private void goToRegister() {
        Fragment fragment = new CreateStoreFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_register_store, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

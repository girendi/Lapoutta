package com.d4ti.lapoutta.fragment.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d4ti.lapoutta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotVerifiedFragment extends Fragment {


    public NotVerifiedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_verified, container, false);
    }

}

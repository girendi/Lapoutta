package com.d4ti.lapoutta.fragment.myStore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.ProductAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.response.ProductResponse;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private View view;
    private RecyclerView rvProduct;
    private BaseApiService baseApiService;
    private TextView tvDataEmpty;
    private int id_store;
    private List<Product> products;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
        initComponent();
        setData();
        return view;
    }

    private void setData() {
        baseApiService.getStoreProduct(id_store).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getProduct();
                    if (!products.isEmpty()){
                        tvDataEmpty.setVisibility(View.INVISIBLE);
                        rvProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        ProductAdapter productAdapter = new ProductAdapter(getContext());
                        productAdapter.setProducts(products);
                        rvProduct.setAdapter(productAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        id_store = SaveSharedPreference.getIdStore(getContext());
        baseApiService = UtilsApi.getAPIService();
        rvProduct = view.findViewById(R.id.rv_product);
        tvDataEmpty = view.findViewById(R.id.data_empty);
    }

}

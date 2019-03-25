package com.d4ti.lapoutta.activity.review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.ReviewAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView rvReview;
    private BaseApiService baseApiService;
    private List<Review> reviews;
    private int id_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = findViewById(R.id.toolbar_review);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Review Produk");

        initComponent();
        setData();
    }

    private void setData() {
        baseApiService.getListReview(id_product).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()){
                    reviews = response.body();
                    if (!reviews.isEmpty()){
                        rvReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ReviewAdapter reviewAdapter = new ReviewAdapter(getApplicationContext());
                        reviewAdapter.setReviews(reviews);
                        rvReview.setAdapter(reviewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("Error Message", t.getMessage());
            }
        });
    }

    private void initComponent() {
        id_product = getIntent().getIntExtra("ID_PRODUCT", 0);
        baseApiService = UtilsApi.getAPIService();
        reviews = new ArrayList<>();
        rvReview = findViewById(R.id.rv_review);
    }
}

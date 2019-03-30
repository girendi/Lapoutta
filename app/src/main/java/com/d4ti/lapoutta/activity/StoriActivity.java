package com.d4ti.lapoutta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.BlogAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Blog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriActivity extends AppCompatActivity {

    private RecyclerView rv_blog;
    private BaseApiService baseApiService;
    private int id_product;
    private List<Blog> blogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stori);

        Toolbar toolbar = findViewById(R.id.toolbar_stori);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Stori Product");

        initComponent();

        setData();
    }

    private void setData() {
        baseApiService.getListBlog(id_product).enqueue(new Callback<List<Blog>>() {
            @Override
            public void onResponse(Call<List<Blog>> call, Response<List<Blog>> response) {
                if (response.isSuccessful()){
                    blogs = response.body();
                    if (!blogs.isEmpty()){
                        rv_blog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        BlogAdapter blogAdapter = new BlogAdapter(getApplicationContext());
                        blogAdapter.setBlogs(blogs);
                        rv_blog.setAdapter(blogAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Blog>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initComponent() {
        id_product = getIntent().getIntExtra("ID_PRODUCT",0);
        blogs = new ArrayList<>();
        rv_blog = findViewById(R.id.list_stori);
        baseApiService = UtilsApi.getAPIService();
    }
}

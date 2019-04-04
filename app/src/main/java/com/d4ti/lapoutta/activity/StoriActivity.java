package com.d4ti.lapoutta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.BlogAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Blog;
import com.d4ti.lapoutta.modal.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriActivity extends AppCompatActivity {

    String url="http://192.168.43.157:1337/images/uploads/";

    private RecyclerView rv_blog;
    private BaseApiService baseApiService;
    private ImageView img_product;
    private TextView tv_name_product;
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
        baseApiService.detailProduct(id_product).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    List<Product> products = response.body();
                    if (!products.isEmpty()){
                        String name = products.get(0).getName();
                        tv_name_product.setText(name);
                        Glide.with(getApplicationContext()).load(url + products.get(0).getImage()).into(img_product);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        img_product = findViewById(R.id.img_product);
        tv_name_product = findViewById(R.id.tv_name_product);
    }
}

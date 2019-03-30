package com.d4ti.lapoutta.activity.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.adapter.BlogAdapter;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Blog;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStorieActivity extends AppCompatActivity {

    private RecyclerView rvStory;
    private EditText etStory;
    private TextView data_empty;
    private Button btnAdd;
    private BaseApiService baseApiService;
    private int id_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storie);
        Toolbar toolbar = findViewById(R.id.toolbar_story);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Story Product");

        initComponent();

        setData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData() {
        baseApiService.createBlog(id_product, 1, etStory.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(AddStorieActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setData() {
        baseApiService.getListBlog(id_product).enqueue(new Callback<List<Blog>>() {
            @Override
            public void onResponse(Call<List<Blog>> call, Response<List<Blog>> response) {
                if (response.isSuccessful()){
                    List<Blog> blogs = response.body();
                    if (!blogs.isEmpty()){
                        data_empty.setVisibility(View.INVISIBLE);
                        rvStory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        BlogAdapter blogAdapter = new BlogAdapter(getApplicationContext());
                        blogAdapter.setBlogs(blogs);
                        rvStory.setAdapter(blogAdapter);
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
        baseApiService = UtilsApi.getAPIService();
        id_product = getIntent().getIntExtra("ID_PRODUCT", 0);
        rvStory = findViewById(R.id.rv_story);
        data_empty = findViewById(R.id.data_empty);
        etStory = findViewById(R.id.et_story);
        btnAdd = findViewById(R.id.btn_add);
    }
}

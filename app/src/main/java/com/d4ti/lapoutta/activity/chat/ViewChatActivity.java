package com.d4ti.lapoutta.activity.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

public class ViewChatActivity extends AppCompatActivity {

    private TextView txtDataEmpty;
    private RecyclerView rv_chat;

    private int id, id_receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getLoggedStatus(this) != true){
            Toast.makeText(this, "Status : " + SaveSharedPreference.getLoggedStatus(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        setContentView(R.layout.activity_view_chat);

        Toolbar toolbar = findViewById(R.id.toolbar_view_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("View Chat");

        id  = SaveSharedPreference.getIdUser(this);
        id_receiver = getIntent().getIntExtra("ID_RECEIVER", 0);
        initComponent();
    }

    private void initComponent() {
        txtDataEmpty = findViewById(R.id.data_empty);
        rv_chat = findViewById(R.id.rv_view_chat);
        rv_chat.setVisibility(View.INVISIBLE);
    }
}

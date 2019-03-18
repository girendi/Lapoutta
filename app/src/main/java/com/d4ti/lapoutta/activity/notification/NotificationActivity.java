package com.d4ti.lapoutta.activity.notification;

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

public class NotificationActivity extends AppCompatActivity {

    private TextView txtDataEmpty;
    private RecyclerView rv_notif;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getLoggedStatus(this) != true){
            Toast.makeText(this, "Status : " + SaveSharedPreference.getLoggedStatus(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = findViewById(R.id.toolbar_notif);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notifikasi");

        id  = SaveSharedPreference.getIdUser(this);

        initComponent();
    }

    private void initComponent() {
        txtDataEmpty = findViewById(R.id.data_empty);
        rv_notif = findViewById(R.id.rv_notif);
        rv_notif.setVisibility(View.INVISIBLE);
    }
}

package com.d4ti.lapoutta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.activity.profile.ProfileActivity;

public class PembayaranActivity extends AppCompatActivity {

    private Button btn_save;
    private EditText et_no_bank_tujuan, et_no_bank_pengirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        initComponent();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
    }

    private void initComponent() {
        btn_save = findViewById(R.id.btn_save);
        et_no_bank_tujuan = findViewById(R.id.et_nomor_bank_tujuan);
        et_no_bank_pengirim = findViewById(R.id.et_nomor_bank_pengirim);
    }
}

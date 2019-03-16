package com.d4ti.lapoutta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.d4ti.lapoutta.activity.AuthActivity;
import com.d4ti.lapoutta.activity.MainActivity;
import com.d4ti.lapoutta.sharedPreferences.SaveSharedPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SaveSharedPreference.setLoggedIn(this, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },3000);
    }
}

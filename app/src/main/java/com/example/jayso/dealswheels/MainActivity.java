package com.example.jayso.dealswheels;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent categoriesIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(categoriesIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

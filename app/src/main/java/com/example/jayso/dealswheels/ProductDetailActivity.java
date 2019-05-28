package com.example.jayso.dealswheels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

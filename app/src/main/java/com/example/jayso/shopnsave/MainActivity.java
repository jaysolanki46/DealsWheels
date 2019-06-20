package com.example.jayso.shopnsave;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
                    if(!haveNetwork()) {
                        Intent intent = new Intent(MainActivity.this, NoInternetActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent categoriesIntent = new Intent(MainActivity.this, CategoryActivity.class);
                        startActivity(categoriesIntent);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
    }

    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MOBILE = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = manager.getAllNetworkInfo();

        for(NetworkInfo info: networkInfos) {

            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if(info.isConnected()) {
                    have_WIFI = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if(info.isConnected()) {
                    have_MOBILE = true;
                }
            }
        }

        return have_WIFI||have_MOBILE;
    }
}

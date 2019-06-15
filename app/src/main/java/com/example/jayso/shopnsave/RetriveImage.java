package com.example.jayso.shopnsave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class RetriveImage extends AsyncTask<Void, Void, Bitmap> {

    private static final String SERVER_ADDRESS = "http://www.solankiinfosolutions.com/";
    String name;

    RetriveImage(String name) {
        this.name = name;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        String url = SERVER_ADDRESS + "pictures/" + name + ".png";

        try {

            URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(1000*30);
            connection.setReadTimeout(1000*30);

            return BitmapFactory.decodeStream((InputStream) connection.getContent(), null ,null);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

package com.example.jayso.shopnsave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    String shareBody = "";
    String shareSubject = "Shop N Save";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ProductActivity.class);
                intent.putExtra("prod_cat_id", getIntent().getStringExtra("prod_cat_id"));
                intent.putExtra("cat_id", getIntent().getStringExtra("cat_id"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.share_button:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                intentShare.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(intentShare, "Share Using"));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Header
        getSupportActionBar().setTitle("Product");

        ImageView imageview_product_image = findViewById(R.id.product_image);
        TextView textview_product_name = findViewById(R.id.product_name);
        TextView textview_price_pak_n_save = findViewById(R.id.price_pak_n_save);
        TextView textview_price_coundown = findViewById(R.id.price_coundown);
        TextView textview_price_new_world = findViewById(R.id.price_new_world);

        // Start Database operations
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        String prod_id = getIntent().getStringExtra("prod_id");

        try {
            result = stmt.executeQuery(
                    "select prod.*, prodprice.* from Products prod LEFT JOIN Product_prices prodprice ON prod.prod_id = prodprice.prod_id where prod.prod_id = "+ prod_id +"");
            while(result.next()){

                Bitmap image = null;
                try {
                    image = new RetriveImage(result.getString("prod_image")).execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                imageview_product_image.setImageBitmap(image);
                String name = result.getString("prod_name");
                textview_product_name.setText(name);
                textview_price_pak_n_save.setText(result.getString("pak_n_save_price"));
                textview_price_coundown.setText(result.getString("coundown_price"));
                textview_price_new_world.setText(result.getString("new_world_price"));

                shareBody = "Hi, hope you well today ! " +
                        "\nHurry !!!! grab your grocery today," +
                        "\n\n" + name + " available in "+
                        "\n Pak N Save : $ " + result.getString("pak_n_save_price") +
                        "\n Coundown : $ " + result.getString("coundown_price") +
                        "\n New World : $ " + result.getString("new_world_price");
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // End Database operations
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.activity_share, menu);
        return true;
    }
}

package com.example.jayso.shopnsave;

import android.content.Intent;
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

                int image = getResources().getIdentifier( result.getString("prod_image"), "drawable", getPackageName());
                String name = result.getString("prod_name");

                imageview_product_image.setBackgroundResource(image);
                textview_product_name.setText(name);

                Float price_paknsave = 0.0f;
                Float price_coundown = 0.0f;
                Float price_newworld = 0.0f;

                String result_price_paknsave = result.getString("pak_n_save_price");
                String result_price_coundown = result.getString("coundown_price");
                String result_price_newworld = result.getString("new_world_price");

                if(result_price_paknsave != null) {
                    price_paknsave = Float.valueOf(result.getString("pak_n_save_price"));
                }

                if(result_price_coundown != null) {
                    price_coundown = Float.valueOf(result.getString("coundown_price"));
                }

                if(result_price_newworld != null) {
                    price_newworld = Float.valueOf(result.getString("new_world_price"));
                }


                if(price_paknsave > 0) {
                   textview_price_pak_n_save.setText("$ " + price_paknsave.toString());
                } else {
                    textview_price_pak_n_save.setText("N/A");
                }

                if(price_coundown > 0) {
                    textview_price_coundown.setText("$ " + price_coundown.toString());
                } else {
                    textview_price_coundown.setText("N/A");
                }

                if(price_newworld > 0) {
                    textview_price_new_world.setText("$ " + price_newworld.toString());
                } else {
                    textview_price_new_world.setText("N/A");
                }

                shareBody = "Hi, hope you well today ! " +
                        "\nHurry !!!! grab your grocery today," +
                        "\n\n" + name + "available in "+
                        "\n Pak N Save : $ " + price_paknsave.toString() +
                        "\n Coundown : $ " + price_coundown.toString() +
                        "\n New World : $ " + price_newworld.toString();
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

package com.example.jayso.shopnsave;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> products;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ProductCategoryActivity.class);
                intent.putExtra("cat_id", getIntent().getStringExtra("cat_id"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Header
        getSupportActionBar().setTitle("Products");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Start Database operations
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        products = new ArrayList<>();
        String prod_cat_id = getIntent().getStringExtra("prod_cat_id");

        try {
            result = stmt.executeQuery(
                    "select prod.*, price.* from Products prod LEFT JOIN Product_prices price ON prod.prod_id = price.prod_id where prod.prod_cat_id ="+ prod_cat_id +"");
            while(result.next()){
                int image = getResources().getIdentifier( result.getString("prod_image"), "drawable", getPackageName());
                products.
                        add(new Product(
                                result.getString("prod_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_id"),
                                result.getString("prod_name"),
                                result.getString("prod_store_counter"),
                                image,
                                Float.valueOf(result.getString("pak_n_save_price")),
                                Float.valueOf(result.getString("coundown_price")),
                                Float.valueOf(result.getString("new_world_price"))));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // End Database operations

        productAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void getProductDetail(View view) {

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("prod_cat_id", getIntent().getStringExtra("prod_cat_id"));

        TextView product_id = (TextView)view.findViewById(R.id.product_id);
        intent.putExtra("prod_id", product_id.getText());

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productName = (TextView) findViewById(R.id.product_name);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(productImage, "product_image");
        pairs[1] = new Pair<View, String>(productName, "product_name");


        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, pairs);

        startActivity(intent, activityOptions.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.activity_search, menu);
        return true;
    }
}

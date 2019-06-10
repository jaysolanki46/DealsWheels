package com.example.jayso.shopnsave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryActivity extends AppCompatActivity {

    List<ProductCategory> productCategories;
    RecyclerView recyclerView;
    ProductCategoryAdapter productCategoryAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, CategoryActivity.class);
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
        setContentView(R.layout.activity_product_categories);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Header
        getSupportActionBar().setTitle("Product Category");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product_categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Start Database operations
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();
        String cat_id = getIntent().getStringExtra("cat_id");

        try {
            result = stmt.executeQuery("select * from Product_categories where cat_id ="+ cat_id +"");
            while(result.next()){
                productCategories.
                        add(new ProductCategory(
                                result.getString("prod_cat_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_name")));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // End Database operations

        productCategoryAdapter = new ProductCategoryAdapter(this, productCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(productCategoryAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void getProducts(View view) {

        Intent intent = new Intent(this, ProductActivity.class);
        TextView product_category_id = (TextView)view.findViewById(R.id.product_category_id);
        intent.putExtra("cat_id", getIntent().getStringExtra("cat_id"));
        intent.putExtra("prod_cat_id", product_category_id.getText());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.activity_search, menu);
        return true;
    }
}
package com.example.jayso.shopnsave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product_categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //String category_id = getIntent().getStringExtra("category_id");

        // Header
        getSupportActionBar().setTitle("Product Category");

        // Database values
        productCategories = new ArrayList<>();
        productCategories.add(new ProductCategory("1", "1", "Fruit 1"));
        productCategories.add(new ProductCategory("2", "1", "Fruit 2"));
        productCategories.add(new ProductCategory("3", "1", "Fruit 3"));
        productCategories.add(new ProductCategory("4", "1", "Fruit 4"));
        productCategories.add(new ProductCategory("6", "1", "Fruit 5"));
        productCategories.add(new ProductCategory("7", "1", "Fruit 6"));
        productCategories.add(new ProductCategory("8", "1", "Fruit 7"));
        productCategories.add(new ProductCategory("9", "1", "Fruit 8"));
        productCategories.add(new ProductCategory("10", "1", "Fruit 9"));
        productCategories.add(new ProductCategory("11", "1", "Fruit 10"));
        productCategories.add(new ProductCategory("12", "1", "Fruit 11"));


        productCategoryAdapter = new ProductCategoryAdapter(this, productCategories);
        recyclerView.setAdapter(productCategoryAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void getProducts(View view) {

        Intent intent = new Intent(this, ProductActivity.class);
        TextView product_category_id = (TextView)view.findViewById(R.id.product_category_id);
        intent.putExtra("product_category_id", product_category_id.getText());
        startActivity(intent);
    }
}
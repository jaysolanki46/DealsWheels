package com.example.jayso.shopnsave;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        setContentView(R.layout.activity_products);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        products = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //String category_id = getIntent().getStringExtra("category_id");

        // Header
        getSupportActionBar().setTitle("Category");

        // Product Listing
        products.add(new Product("1", R.drawable.fruits, "Fruit name"));
        products.add(new Product("2", R.drawable.fruits, "Fruit name"));
        products.add(new Product("3", R.drawable.fruits, "Fruit name"));
        products.add(new Product("4", R.drawable.fruits, "Fruit name"));
        products.add(new Product("5", R.drawable.fruits, "Fruit name"));
        products.add(new Product("6", R.drawable.fruits, "Fruit name"));
        products.add(new Product("7", R.drawable.fruits, "Fruit name"));

        products.add(new Product("8", R.drawable.fruits, "Fruit name"));
        products.add(new Product("9", R.drawable.fruits, "Fruit name"));
        products.add(new Product("10", R.drawable.fruits, "Fruit name"));
        products.add(new Product("11", R.drawable.fruits, "Fruit name"));
        products.add(new Product("12", R.drawable.fruits, "Fruit name"));
        products.add(new Product("13", R.drawable.fruits, "Fruit name"));
        products.add(new Product("14", R.drawable.fruits, "Fruit name"));


        productAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void getProductDetail(View view) {

        Intent intent = new Intent(this, ProductDetailActivity.class);
        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productName = (TextView) findViewById(R.id.product_name);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(productImage, "product_image");
        pairs[1] = new Pair<View, String>(productName, "product_name");


        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, pairs);

        startActivity(intent, activityOptions.toBundle());
    }
}

package com.example.jayso.shopnsave;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> products;
    boolean SEARCH_FLAG = true;

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
            case R.id.voice_bar_id:
                getMic();
                return true;
            case R.id.refresh_id:
                search("all");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<Product> getProducts(String product_name) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        products = new ArrayList<>();
        String prod_cat_id = getIntent().getStringExtra("prod_cat_id");

        try {
            if(product_name == "all") {
                result = stmt.executeQuery(
                        "select prod.*, price.* from Products prod LEFT JOIN Product_prices price ON prod.prod_id = price.prod_id where prod.prod_cat_id ="+ prod_cat_id +"");
            } else {
                result = stmt.executeQuery(
                        "select prod.*, price.* from Products prod LEFT JOIN Product_prices price ON prod.prod_id = price.prod_id where prod.prod_cat_id ="+ prod_cat_id +" and prod.prod_name LIKE '%"+ product_name +"%'");

                if(!result.next()) {
                    Toast.makeText(getApplicationContext(), "No result found !", Toast.LENGTH_LONG).show();
                    result = stmt.executeQuery(
                            "select prod.*, price.* from Products prod LEFT JOIN Product_prices price ON prod.prod_id = price.prod_id where prod.prod_cat_id ="+ prod_cat_id +"");
                } else {
                    do {
                        products.
                                add(new Product(
                                        result.getString("prod_id"),
                                        result.getString("cat_id"),
                                        result.getString("prod_cat_id"),
                                        result.getString("prod_name"),
                                        result.getString("prod_store_counter"),
                                        result.getString("prod_image"),
                                        result.getString("pak_n_save_price"),
                                        result.getString("coundown_price"),
                                        result.getString("new_world_price")));
                    } while(result.next());
                }
            }

            while(result.next()){

                products.
                        add(new Product(
                                result.getString("prod_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_id"),
                                result.getString("prod_name"),
                                result.getString("prod_store_counter"),
                                result.getString("prod_image"),
                                result.getString("pak_n_save_price"),
                                result.getString("coundown_price"),
                                result.getString("new_world_price")));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void search(String product_name) {

        products = getProducts(product_name);

        productAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        SEARCH_FLAG = true;

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Header
        getSupportActionBar().setTitle("Products");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = getProducts("all");

        productAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void getProductDetail(View view) {

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("prod_cat_id", getIntent().getStringExtra("prod_cat_id"));

        TextView product_id = (TextView)view.findViewById(R.id.product_id);
        intent.putExtra("prod_id", product_id.getText());
        intent.putExtra("cat_id", getIntent().getStringExtra("cat_id"));

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
        MenuItem searchItem = menu.findItem(R.id.search_bar_id);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(SEARCH_FLAG) {
                    SEARCH_FLAG = false;
                } else {
                    search(newText);
                }
                return true;
            }
        });


        return true;
    }

    public void getMic() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your device is not supported this feature !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search(result.get(0).toString());
                }
                break;
        }
    }
}

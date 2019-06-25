package com.example.jayso.shopnsave;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductCategoryActivity extends AppCompatActivity {

    List<ProductCategory> productCategories;
    RecyclerView recyclerView;
    ProductCategoryAdapter productCategoryAdapter;
    boolean SEARCH_FLAG = true;
    Spinner spinnerCategory;
    List<Category> categories = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public List<ProductCategory> getSearchedProductCategories(String product_category_name) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();

        try {
            if(product_category_name == "all") {
                result = stmt.executeQuery("select * from Product_categories");
            } else {
                result = stmt.executeQuery("select * from Product_categories where prod_cat_name LIKE '%"+ product_category_name +"%'");

                if(!result.next()) {
                    Toast.makeText(getApplicationContext(), "No result found !", Toast.LENGTH_LONG).show();
                    result = stmt.executeQuery("select * from Product_categories");
                } else {
                    do {
                        productCategories.
                                add(new ProductCategory(
                                        result.getString("prod_cat_id"),
                                        result.getString("cat_id"),
                                        result.getString("prod_cat_name"),
                                        result.getString("prod_cat_image")));
                    } while(result.next());
                }
            }

            while(result.next()){
                productCategories.
                        add(new ProductCategory(
                                result.getString("prod_cat_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_name"),
                                result.getString("prod_cat_image")));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategories;
    }

    public List<ProductCategory> getProductCategories(String cat_id) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();

        try {
            if(cat_id == "0") {
                result = stmt.executeQuery("select * from Product_categories");
            } else {
                result = stmt.executeQuery("select * from Product_categories where cat_id ="+ cat_id +"");

                if(!result.next()) {
                    Toast.makeText(getApplicationContext(), "No result found !", Toast.LENGTH_LONG).show();
                    result = stmt.executeQuery("select * from Product_categories");
                } else {
                    do {
                        productCategories.
                                add(new ProductCategory(
                                        result.getString("prod_cat_id"),
                                        result.getString("cat_id"),
                                        result.getString("prod_cat_name"),
                                        result.getString("prod_cat_image")));
                    } while(result.next());
                }
            }

            while(result.next()){
                productCategories.
                        add(new ProductCategory(
                                result.getString("prod_cat_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_name"),
                                result.getString("prod_cat_image")));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategories;
    }

    public void search(String product_category_name) {

        productCategories = getSearchedProductCategories(product_category_name);

        productCategoryAdapter = new ProductCategoryAdapter(this, productCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(productCategoryAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        SEARCH_FLAG = true;

        // Header
        getSupportActionBar().setTitle("Shop N Save");

        categories = getCategories();
        spinnerCategory = (Spinner) findViewById(R.id.spinner_categories);
        ArrayAdapter< Category > categoryArrayAdapter =
                new ArrayAdapter < Category > (ProductCategoryActivity.this, android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryArrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Category category = (Category) parent.getSelectedItem();
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product_categories);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProductCategoryActivity.this));

                productCategories = getProductCategories(category.getCategory_id());

                productCategoryAdapter = new ProductCategoryAdapter(ProductCategoryActivity.this, productCategories);
                recyclerView.setLayoutManager(new GridLayoutManager(ProductCategoryActivity.this, 2));

                recyclerView.setAdapter(productCategoryAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public List<Category> getCategories() {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        categories = new ArrayList<>();
        categories.add(new Category(
                "0",
                "All Categories", ""));
        try {
            result = stmt.executeQuery("select * from Categories");
            while(result.next()){

                categories.add(new Category(
                        result.getString("cat_id"),
                        result.getString("cat_name"), ""));
            }
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
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
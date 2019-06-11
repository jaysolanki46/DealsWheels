package com.example.jayso.shopnsave;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity {

    List<Category> categories;

    public List<Category> getcategories(String category_name) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        categories = new ArrayList<>();
        try {
            if(category_name == "all") {
                result = stmt.executeQuery("select * from Categories");
            } else {
                result = stmt.executeQuery("select * from Categories where cat_name LIKE '"+ category_name +"%'");
            }

            while(result.next()){
                int image = getResources().getIdentifier( result.getString("cat_image"), "drawable", getPackageName());
                categories.add(new Category(
                        result.getString("cat_id"),
                        result.getString("cat_name"), image));
            }
            conn.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void search(String category_name) {

        categories = getcategories(category_name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //Header
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_shop_n_save);

        // get from database
        categories = getcategories("all");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(categoryAdapter);
    }

    public void getProductCategories(View view) {
        Intent intent = new Intent(this, ProductCategoryActivity.class);
        TextView category_id = (TextView)view.findViewById(R.id.category_id);
        intent.putExtra("cat_id", category_id.getText());
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search(newText);
                return true;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voice_bar_id:
                getMic();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    // Grab voice result -> result.get(0)
                    System.err.print(result.get(0));
                }
                break;
        }
    }
}

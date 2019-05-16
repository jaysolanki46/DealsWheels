package com.example.jayso.dealswheels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categories = new ArrayList<>();
        categories.add(new Category("1", "Fruits", R.drawable.fruits));
        categories.add(new Category("2", "Meats", R.drawable.meats));
        categories.add(new Category("3", "Cosmetic", R.drawable.cosmetics));
        categories.add(new Category("4", "Cereals", R.drawable.cereals));
        categories.add(new Category("5", "Rice", R.drawable.rice));
        categories.add(new Category("6", "Chocolates", R.drawable.chocolates));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(categoryAdapter);
    }
}

package com.example.jayso.shopnsave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        int images[] = {R.drawable.paknsave, R.drawable.coundown, R.drawable.newworld};
        viewFlipper = findViewById(R.id.view_flipper);

        for(int image: images){
            flipperImages(image);
        }


        categories = new ArrayList<>();
        categories.add(new Category("1", "Fruits", R.drawable.fruits));
        categories.add(new Category("2", "Meats", R.drawable.meats));
        categories.add(new Category("3", "Cosmetic", R.drawable.cosmetics));
        categories.add(new Category("4", "Cereals", R.drawable.cereals));
        categories.add(new Category("5", "Rice", R.drawable.rice));
        categories.add(new Category("6", "Chocolates", R.drawable.chocolates));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(categoryAdapter);
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    public void getProductCategories(View view) {

        Intent intent = new Intent(this, ProductCategoryActivity.class);
        TextView category_id = (TextView)view.findViewById(R.id.category_id);
        System.out.print(category_id.getText());
        intent.putExtra("category_id", category_id.getText());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.activity_search, menu);
        return true;
    }
}

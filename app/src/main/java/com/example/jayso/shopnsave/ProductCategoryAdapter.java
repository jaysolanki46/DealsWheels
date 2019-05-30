package com.example.jayso.shopnsave;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ListViewHolder> {

    private Context context;
    private List<ProductCategory> productCategories;
    private int lastPosition = -5;

    public ProductCategoryAdapter(ProductCategoryActivity context, List<ProductCategory> productCategories) {

        this.context = context;
        this.productCategories = productCategories;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_product_category_item, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryAdapter.ListViewHolder listViewHolder, int i) {
        ProductCategory productCategory = productCategories.get(i);
        listViewHolder.productCategoryId.setText(productCategory.getProd_cat_id());
        listViewHolder.productCategoryName.setText(productCategory.getProd_cat_name());
        setAnimation(listViewHolder.itemView, i);
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView productCategoryId;
        TextView productCategoryName;

        public ListViewHolder(@NonNull View itemView) {

            super(itemView);
            productCategoryId = itemView.findViewById(R.id.product_category_id);
            productCategoryName = itemView.findViewById(R.id.product_category_name);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

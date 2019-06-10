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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ListViewHolder> {

    private Context context;
    private List<Product> products;
    private int lastPosition = -5;

    public ProductAdapter(ProductActivity context, List<Product> products) {

        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_product_item, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        Product product = products.get(i);
        listViewHolder.product_id .setText(product.getProduct_id());
        listViewHolder.product_name.setText(product.getProduct_name());
        listViewHolder.product_image.setBackgroundResource(product.getProduct_image());

        // Max price
        if(product.getProduct_pak_n_save_price() > product.getProduct_coundown_price()
                && product.getProduct_pak_n_save_price() > product.getProduct_new_world_price()) {

            listViewHolder.product_max_price.setText(
                    String.valueOf(product.getProduct_pak_n_save_price()));

        } else if(product.getProduct_coundown_price() > product.getProduct_pak_n_save_price()
                && product.getProduct_coundown_price() > product.getProduct_new_world_price()) {

            listViewHolder.product_max_price.setText(
                    String.valueOf(product.getProduct_coundown_price()));
        } else {

            listViewHolder.product_max_price.setText(
                    String.valueOf(product.getProduct_new_world_price()));
        }

        // Min price
        if(product.getProduct_pak_n_save_price() < product.getProduct_coundown_price()
                && product.getProduct_pak_n_save_price() < product.getProduct_new_world_price()) {

            listViewHolder.product_min_price.setText(
                    String.valueOf(product.getProduct_pak_n_save_price()));

        } else if(product.getProduct_coundown_price() < product.getProduct_pak_n_save_price()
                && product.getProduct_coundown_price() < product.getProduct_new_world_price()) {

            listViewHolder.product_min_price.setText(
                    String.valueOf(product.getProduct_coundown_price()));
        } else {

            listViewHolder.product_min_price.setText(
                    String.valueOf(product.getProduct_new_world_price()));
        }

        listViewHolder.product_store_count.setText(product.getProduct_store_count() + " Stores");
        setAnimation(listViewHolder.itemView, i);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView product_id;
        TextView product_name;
        ImageView product_image;
        TextView product_max_price;
        TextView product_min_price;
        TextView product_store_count;

        public ListViewHolder(@NonNull View itemView) {

            super(itemView);
            product_id = itemView.findViewById(R.id.product_id);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
            product_max_price = itemView.findViewById(R.id.product_price_max);
            product_min_price = itemView.findViewById(R.id.product_price_min);
            product_store_count = itemView.findViewById(R.id.product_store_counter);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

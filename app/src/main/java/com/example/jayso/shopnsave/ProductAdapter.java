package com.example.jayso.shopnsave;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        listViewHolder.product_id.setText(product.getProduct_id());
        listViewHolder.product_name.setText(product.getProduct_name());

        Bitmap image = null;
        try {
            image = new RetriveImage(products.get(i).getProduct_image().toString()).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(image != null) {
            listViewHolder.product_image.setImageBitmap(image);
        } else {
            listViewHolder.product_image.setImageResource(R.drawable.icon_no_image_found);
        }

        Map<Float, Integer> prices = new HashMap<>();

        if (!product.getProduct_pak_n_save_price().equals("N/A")) {
            prices.put(Float.valueOf(product.getProduct_pak_n_save_price()), R.drawable.icon_pak_n_save);
        }

        if (!product.getProduct_coundown_price().equals("N/A")) {
            prices.put(Float.valueOf(product.getProduct_coundown_price()), R.drawable.icon_countdown);
        }

        if (!product.getProduct_new_world_price().equals("N/A")) {
            prices.put(Float.valueOf(product.getProduct_new_world_price()),R.drawable.icon_new_world);
        }

        if(prices.isEmpty()) {
            listViewHolder.product_max_price.setText("N/A");
            listViewHolder.product_min_price.setText("N/A");
        } else {
            Map.Entry<Float,Integer>  initialEntry = prices.entrySet().iterator().next();
            Float max_price = initialEntry.getKey();
            Integer max_label = initialEntry.getValue();
            Float min_price = initialEntry.getKey();
            Integer min_label = initialEntry.getValue();

            for (Map.Entry<Float,Integer> price : prices.entrySet()) {
                if(price.getKey() > max_price){
                    max_price = price.getKey();
                    max_label = price.getValue();
                }
            }

            for (Map.Entry<Float,Integer> price : prices.entrySet()) {
                if(price.getKey() < min_price){
                    min_price = price.getKey();
                    min_label = price.getValue();
                }
            }

            listViewHolder.product_max_price.setText(String.valueOf(max_price));
            listViewHolder.product_max_price_label_org.setImageResource(max_label);
            listViewHolder.product_min_price.setText(String.valueOf(min_price));
            listViewHolder.product_min_price_label_org.setImageResource(min_label);
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
        ImageView product_max_price_label_org;
        TextView product_min_price;
        ImageView product_min_price_label_org;
        TextView product_store_count;

        public ListViewHolder(@NonNull View itemView) {

            super(itemView);
            product_id = itemView.findViewById(R.id.product_id);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
            product_max_price = itemView.findViewById(R.id.product_price_max);
            product_max_price_label_org = itemView.findViewById(R.id.product_price_max_org_img);
            product_min_price = itemView.findViewById(R.id.product_price_min);
            product_min_price_label_org = itemView.findViewById(R.id.product_price_min_org_img);
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

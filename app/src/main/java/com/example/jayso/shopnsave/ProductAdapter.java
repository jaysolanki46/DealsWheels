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
        listViewHolder.productId .setText(product.getProduct_id());
        listViewHolder.productName.setText(product.getProduct_name());
        listViewHolder.productImage.setBackgroundResource(product.getProduct_image());
        setAnimation(listViewHolder.itemView, i);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productId;
        TextView productName;

        public ListViewHolder(@NonNull View itemView) {

            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productId = itemView.findViewById(R.id.product_id);
            productName = itemView.findViewById(R.id.product_name);
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

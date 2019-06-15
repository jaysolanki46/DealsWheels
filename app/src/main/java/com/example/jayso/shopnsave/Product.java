package com.example.jayso.shopnsave;

public class Product {

    private String product_id;
    private String category_id;
    private String product_category_id;
    private String product_name;
    private String product_store_count;
    private String product_image;
    private float product_pak_n_save_price;
    private float product_coundown_price;
    private float product_new_world_price;

    public Product(){}

    public Product(String product_id, String category_id, String product_category_id, String product_name, String product_store_count, String product_image, float product_pak_n_save_price, float product_coundown_price, float product_new_world_price) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_category_id = product_category_id;
        this.product_name = product_name;
        this.product_store_count = product_store_count;
        this.product_image = product_image;
        this.product_pak_n_save_price = product_pak_n_save_price;
        this.product_coundown_price = product_coundown_price;
        this.product_new_world_price = product_new_world_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_store_count() {
        return product_store_count;
    }

    public void setProduct_store_count(String product_store_count) {
        this.product_store_count = product_store_count;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public float getProduct_pak_n_save_price() {
        return product_pak_n_save_price;
    }

    public void setProduct_pak_n_save_price(float product_pak_n_save_price) {
        this.product_pak_n_save_price = product_pak_n_save_price;
    }

    public float getProduct_coundown_price() {
        return product_coundown_price;
    }

    public void setProduct_coundown_price(float product_coundown_price) {
        this.product_coundown_price = product_coundown_price;
    }

    public float getProduct_new_world_price() {
        return product_new_world_price;
    }

    public void setProduct_new_world_price(float product_new_world_price) {
        this.product_new_world_price = product_new_world_price;
    }
}

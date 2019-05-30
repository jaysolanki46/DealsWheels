package com.example.jayso.shopnsave;

public class ProductCategory {

    private String prod_cat_id;
    private String cat_id;
    private String prod_cat_name;

    public ProductCategory() {}

    public ProductCategory(String prod_cat_id, String cat_id, String prod_cat_name) {
        this.prod_cat_id = prod_cat_id;
        this.cat_id = cat_id;
        this.prod_cat_name = prod_cat_name;
    }

    public String getProd_cat_id() {
        return prod_cat_id;
    }

    public void setProd_cat_id(String prod_cat_id) {
        this.prod_cat_id = prod_cat_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getProd_cat_name() {
        return prod_cat_name;
    }

    public void setProd_cat_name(String prod_cat_name) {
        this.prod_cat_name = prod_cat_name;
    }
}

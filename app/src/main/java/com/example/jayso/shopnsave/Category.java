package com.example.jayso.shopnsave;

public class Category {

    private String category_id;
    private String category_image;
    private String category_title;

    public Category(){}

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public Category(String category_id, String category_title, String category_image) {
        this.category_id = category_id;
        this.category_image = category_image;
        this.category_title = category_title;
    }

    @Override
    public String toString() {
        return category_title;
    }
}

package com.example.admin_application.Home_Page.Product_Pachage;

public class Model_product {

    private int id;
    private String username,product_name,details_product,price_product,count_product,image_product;

    public Model_product(int id, String username, String product_name, String details_product, String price_product, String count_product, String image_product) {
        this.id = id;
        this.username = username;
        this.product_name = product_name;
        this.details_product = details_product;
        this.price_product = price_product;
        this.count_product = count_product;
        this.image_product = image_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDetails_product() {
        return details_product;
    }

    public void setDetails_product(String details_product) {
        this.details_product = details_product;
    }

    public String getPrice_product() {
        return price_product;
    }

    public void setPrice_product(String price_product) {
        this.price_product = price_product;
    }

    public String getCount_product() {
        return count_product;
    }

    public void setCount_product(String count_product) {
        this.count_product = count_product;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }
}

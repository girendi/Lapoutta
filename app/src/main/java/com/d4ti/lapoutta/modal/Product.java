package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private double price;
    @SerializedName("stock")
    private int stock;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("id_category")
    private int id_category;
    @SerializedName("id_size")
    private int id_size;
    @SerializedName("id_store")
    private int id_store;
    @SerializedName("id_product_status")
    private int id_product_status;

    public Product(int id, String name, double price, int stock, String image, String description, int id_category, int id_size, int id_store, int id_product_status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.description = description;
        this.id_category = id_category;
        this.id_size = id_size;
        this.id_store = id_store;
        this.id_product_status = id_product_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public int getId_product_status() {
        return id_product_status;
    }

    public void setId_product_status(int id_product_status) {
        this.id_product_status = id_product_status;
    }
}

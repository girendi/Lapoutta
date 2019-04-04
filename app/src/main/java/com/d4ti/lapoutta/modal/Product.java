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
    @SerializedName("gambar")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("id_category")
    private Category category;
    @SerializedName("id_size")
    private int id_size;
    @SerializedName("id_store")
    private Store store;
    @SerializedName("id_product_status")
    private ProductStatus productStatus;

    public Product() {
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

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public Category getCategory() {
        return category;
    }

    public Store getStore() {
        return store;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }
}

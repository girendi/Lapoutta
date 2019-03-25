package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_product")
    private int id_product;

    public Review() {
    }

    public Review(int id, String body, int id_customer, int id_product) {
        this.id = id;
        this.body = body;
        this.id_customer = id_customer;
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
}

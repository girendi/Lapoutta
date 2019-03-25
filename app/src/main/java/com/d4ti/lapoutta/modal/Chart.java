package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Chart {
    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("is_active")
    private boolean is_active;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_product")
    private int id_product;

    public Chart() {
    }

    public Chart(int id, int quantity, boolean is_active, int id_customer, int id_product) {
        this.id = id;
        this.quantity = quantity;
        this.is_active = is_active;
        this.id_customer = id_customer;
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
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

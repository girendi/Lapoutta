package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class DetailTransaction {
    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("sub_total")
    private double sub_total;
    @SerializedName("id_product")
    private int id_product;
    @SerializedName("id_transaction")
    private Transaction transaction;

    public DetailTransaction() {
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

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}

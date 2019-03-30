package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("id")
    private int id;
    @SerializedName("transaction_date")
    private String transaction_date;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_transaction_status")
    private int id_transaction_status;

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_transaction_status() {
        return id_transaction_status;
    }

    public void setId_transaction_status(int id_transaction_status) {
        this.id_transaction_status = id_transaction_status;
    }
}

package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Notif {
    @SerializedName("id")
    private int id;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_order")
    private int id_order;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public Notif() {
    }

    public Notif(int id, int id_customer, int id_order, String title, String body) {
        this.id = id;
        this.id_customer = id_customer;
        this.id_order = id_order;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

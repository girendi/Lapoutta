package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Blog {
    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("type")
    private int type;
    @SerializedName("id_store")
    private Store store;

    public Blog() {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}

package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class ProductStatus {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public ProductStatus() {
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
}

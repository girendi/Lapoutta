package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("no_telp")
    @Expose
    private String no_telp;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("id_role")
    @Expose
    private int id_role;

    public Customer(int id, String name, String no_telp, String image, int id_role) {
        this.id = id;
        this.name = name;
        this.no_telp = no_telp;
        this.image = image;
        this.id_role = id_role;
    }

    public Customer() {
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

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
}

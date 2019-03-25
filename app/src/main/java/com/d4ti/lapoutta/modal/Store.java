package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("no_telp")
    private String no_telp;
    @SerializedName("address")
    private String address;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_status")
    private int id_status;

    public Store() {
    }

    public Store(int id, String name, String no_telp, String address, int id_customer, int id_status) {
        this.id = id;
        this.name = name;
        this.no_telp = no_telp;
        this.address = address;
        this.id_customer = id_customer;
        this.id_status = id_status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }
}

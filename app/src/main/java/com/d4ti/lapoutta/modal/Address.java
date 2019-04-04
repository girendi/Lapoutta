package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("id")
    private int id;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("address")
    private String address;
    @SerializedName("province")
    private String province;
    @SerializedName("kabupatenKota")
    private String kabupatenKota;

    public Address() {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getKabupatenKota() {
        return kabupatenKota;
    }

    public void setKabupatenKota(String kabupatenKota) {
        this.kabupatenKota = kabupatenKota;
    }
}

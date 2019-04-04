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
    @SerializedName("no_KTP")
    private String no_KTP;
    @SerializedName("no_Rekening")
    private String no_Rekening;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kabupatenKota")
    private String kabupatenKota;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("id_store_status")
    private int id_store_status;

    public Store() {
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

    public String getNo_KTP() {
        return no_KTP;
    }

    public void setNo_KTP(String no_KTP) {
        this.no_KTP = no_KTP;
    }

    public String getNo_Rekening() {
        return no_Rekening;
    }

    public void setNo_Rekening(String no_Rekening) {
        this.no_Rekening = no_Rekening;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupatenKota() {
        return kabupatenKota;
    }

    public void setKabupatenKota(String kabupatenKota) {
        this.kabupatenKota = kabupatenKota;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_store_status() {
        return id_store_status;
    }

    public void setId_store_status(int id_store_status) {
        this.id_store_status = id_store_status;
    }
}

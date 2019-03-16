package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Slide {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("gambar")
    private String gambar;

    public Slide(int id, String name, String gambar) {
        this.id = id;
        this.name = name;
        this.gambar = gambar;
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

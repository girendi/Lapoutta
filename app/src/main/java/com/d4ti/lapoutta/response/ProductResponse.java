package com.d4ti.lapoutta.response;

import com.d4ti.lapoutta.modal.Category;
import com.d4ti.lapoutta.modal.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("List")
    private List<Product> products;

    @SerializedName("id_category")
    private Category category;

    public ProductResponse() {
    }

    public Category getCategory() {
        return category;
    }

    public List<Product> getProduct() {
        return products;
    }
}

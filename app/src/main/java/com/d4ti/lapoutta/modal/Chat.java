package com.d4ti.lapoutta.modal;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    private int id;

    @SerializedName("id_sender")
    private Customer customer_sender;

    @SerializedName("id_receiver")
    private Customer customer_receiver;

    @SerializedName("message")
    private String message;

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer_sender() {
        return customer_sender;
    }

    public void setCustomer_sender(Customer customer_sender) {
        this.customer_sender = customer_sender;
    }

    public Customer getCustomer_receiver() {
        return customer_receiver;
    }

    public void setCustomer_receiver(Customer customer_receiver) {
        this.customer_receiver = customer_receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

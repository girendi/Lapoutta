package com.d4ti.lapoutta.apiHelper;

import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Slide;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.response.ProductResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login/mobile")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);
    @FormUrlEncoded
    @POST("register/mobile")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("detail/user/mobile")
    Call<List<Customer>> detailCustomer(@Field("id") int id);

    @GET("list/slide")
    Call<List<Slide>> getSlide();

    @FormUrlEncoded
    @POST("change/profile/mobile")
    Call<ResponseBody> updateProfile(@Field("name") String name,
                                     @Field("no_telp") String no_telp);

    @FormUrlEncoded
    @POST("change/password/mobile")
    Call<ResponseBody> updatePassword(@Field("password") String password,
                                      @Field("new_password") String new_password,
                                      @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("create/product")
    Call<ResponseBody> createProduct(@Field("name") String name,
                                     @Field("price") double price,
                                     @Field("stock") int stock,
                                     @Field("image") String image,
                                     @Field("description") String description,
                                     @Field("id_category") int id_category,
                                     @Field("id_size") int id_size,
                                     @Field("id_store") int id_store,
                                     @Field("id_product_status") int id_product_status);

    @GET("list/product/mobile")
    Call<ProductResponse> getProduct();

    @FormUrlEncoded
    @POST("detail/product/mobile")
    Call<List<Product>> detailProduct(@Field("id") int id);

    @FormUrlEncoded
    @POST("store")
    Call<ResponseBody> createStore(@Field("name") String name,
                                   @Field("no_telp") String no_telp,
                                   @Field("address") String address,
                                   @Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("detail/store/mobile")
    Call<List<Store>> detailStore(@Field("id") int id);
}

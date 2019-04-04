package com.d4ti.lapoutta.apiHelper;

import com.d4ti.lapoutta.modal.Address;
import com.d4ti.lapoutta.modal.Blog;
import com.d4ti.lapoutta.modal.Chart;
import com.d4ti.lapoutta.modal.Chat;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.modal.DetailTransaction;
import com.d4ti.lapoutta.modal.Notif;
import com.d4ti.lapoutta.modal.Product;
import com.d4ti.lapoutta.modal.Review;
import com.d4ti.lapoutta.modal.Slide;
import com.d4ti.lapoutta.modal.Store;
import com.d4ti.lapoutta.modal.Transaction;
import com.d4ti.lapoutta.response.ProductResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
    Call<ResponseBody> updateProfile(@Field("id") int id,
                                     @Field("name") String name,
                                     @Field("no_telp") String no_telp);

    @Multipart
    @POST("change/profil/mobile")
    Call<ResponseBody> updateImage(@Path("id") int id,
                                   @Part MultipartBody.Part image);

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
                                     @Field("description") String description,
                                     @Field("id_category") int id_category,
                                     @Field("id_size") int id_size,
                                     @Field("id_store") int id_store,
                                     @Field("id_product_status") int id_product_status);

    @FormUrlEncoded
    @POST("update/product")
    Call<ResponseBody> updateProduct(@Field("id") int id,
                                @Field("name") String name,
                                @Field("price") double price,
                                @Field("stock") int stock,
                                @Field("description") String description);

    @GET("list/product/mobile")
    Call<ProductResponse> getProduct();

    @FormUrlEncoded
    @POST("lidt/product/store")
    Call<ProductResponse> getStoreProduct(@Field("id_store") int id_store);

    @FormUrlEncoded
    @POST("detail/product/mobile")
    Call<List<Product>> detailProduct(@Field("id") int id);

    @Multipart
    @POST("add/image/product")
    Call<ResponseBody> updateProduct(@Part MultipartBody.Part gambar,
                                   @Path("id") RequestBody id);

    @FormUrlEncoded
    @POST("store")
    Call<Store> createStore(@Field("name") String name,
                            @Field("no_telp") String no_telp,
                            @Field("address") String address,
                            @Field("provinsi") String provinsi,
                            @Field("kabupatenKota") String kabupatenKota,
                            @Field("no_KTP") String no_KTP,
                            @Field("no_Rekening") String no_Rekening,
                            @Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("update/store")
    Call<List<Store>> updateStore(@Field("id_customer") int id_customer,
                                  @Field("name") String name,
                                  @Field("no_telp") String no_telp,
                                  @Field("address") String address,
                                  @Field("provinsi") String provinsi,
                                  @Field("kabupatenKota") String kabupatenKota,
                                  @Field("no_KTP") String no_KTP,
                                  @Field("no_Rekening") String no_Rekening);

    @FormUrlEncoded
    @POST("detail/store/mobile")
    Call<List<Store>> detailStore(@Field("id") int id);

    @FormUrlEncoded
    @POST("detail/store/mobilecus")
    Call<List<Store>> detailStore2(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("list/chat")
    Call<List<Chat>> listChat(@Field("id_sender") int id_sender);

    @FormUrlEncoded
    @POST("create/chat")
    Call<ResponseBody> createChat(@Field("id_sender") int id_sender,
                                  @Field("id_receiver") int id_receiver,
                                  @Field("message") String message);

    @FormUrlEncoded
    @POST("list/review")
    Call<List<Review>> getListReview(@Field("id_product") int id_product);

    @FormUrlEncoded
    @POST("create/review")
    Call<ResponseBody> createReview(@Field("id_customer") int id_customer,
                                    @Field("id_product") int id_product,
                                    @Field("body") String body);

    @FormUrlEncoded
    @POST("list/notif")
    Call<List<Notif>> getListNotif(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("list/cart")
    Call<List<Chart>> getListChart(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("create/cart")
    Call<Chart> createChart(@Field("quantity") int quantity,
                            @Field("is_active") int is_active,
                            @Field("id_customer") int id_customer,
                            @Field("id_product") int id_product);

    @FormUrlEncoded
    @POST("update/cart")
    Call<ResponseBody> updateChart(@Field("id") int id,
                            @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("delete/cart")
    Call<ResponseBody> deleteChart(@Field("id") int id);

    @FormUrlEncoded
    @POST("list/blog")
    Call<List<Blog>> getListBlog(@Field("id_product") int id_product);

    @FormUrlEncoded
    @POST("create/blog")
    Call<ResponseBody> createBlog(@Field("id_product") int id_product,
                                  @Field("type") int type,
                                  @Field("body") String body);

    @FormUrlEncoded
    @POST("belisekarang")
    Call<List<DetailTransaction>> buyNow(@Field("id_customer") int id_customer,
                                         @Field("quantity") int quantity,
                                         @Field("id_product") int id_product);

    @FormUrlEncoded
    @POST("checkout")
    Call<List<DetailTransaction>> checkout(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("list/transaction/store")
    Call<List<Transaction>> listTransactionStore(@Field("id_store") int id_store);

    @FormUrlEncoded
    @POST("list/api/customer")
    Call<List<DetailTransaction>> listDetailTransaction(@Field("id_transaction") int id_transaction);

    @FormUrlEncoded
    @POST("list/transksi/user")
    Call<List<Transaction>> listTransactionUser(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("update/shipment")
    Call<List<Transaction>> updateTransactionShipment(@Field("id") int id,
                                                      @Field("id_shipment") int id_shipment);

    @FormUrlEncoded
    @POST("create/address")
    Call<List<Address>> createAddress(@Field("id_customer") int id_customer,
                                   @Field("address") String address,
                                   @Field("province") String province,
                                   @Field("kabupatenKota") String kabupatenKota);


}

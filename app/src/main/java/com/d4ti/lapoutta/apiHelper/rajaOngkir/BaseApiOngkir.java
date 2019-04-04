package com.d4ti.lapoutta.apiHelper.rajaOngkir;

import com.d4ti.lapoutta.modal.rajaOngkir.city.ResponseCity;
import com.d4ti.lapoutta.modal.rajaOngkir.cost.ResponseCost;
import com.d4ti.lapoutta.modal.rajaOngkir.province.ResponseProvince;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiOngkir {

    @GET("province")
    Call<ResponseProvince> getProvince(@Header("key") String key);

    @GET("city")
    Call<ResponseCity> getCity(@Header("key") String key,
                               @Path("province") String province);

    @FormUrlEncoded
    @POST("cost")
    Call<ResponseCost> getCost(@Header("key") String key,
                               @Field("origin") String origin,
                               @Field("destination") String destination,
                               @Field("weight") int weight,
                               @Field("courier") String courier);
}

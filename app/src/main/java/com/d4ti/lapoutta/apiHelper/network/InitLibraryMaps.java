package com.d4ti.lapoutta.apiHelper.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitLibraryMaps {
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    public static Retrofit setInit(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApiServicesMaps getInstance(){
        return setInit().create(ApiServicesMaps.class);
    }
}

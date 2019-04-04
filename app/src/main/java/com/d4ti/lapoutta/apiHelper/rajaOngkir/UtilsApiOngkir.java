package com.d4ti.lapoutta.apiHelper.rajaOngkir;

public class UtilsApiOngkir {
    public static final String BASE_URL_API_ONGKIR = "https://api.rajaongkir.com/starter/";

    public static BaseApiOngkir getApiOngkir(){
        return RetrofitClientOngkir.getClient(BASE_URL_API_ONGKIR).create(BaseApiOngkir.class);
    }
}

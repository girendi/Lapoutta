package com.d4ti.lapoutta.apiHelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.43.157:1337/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

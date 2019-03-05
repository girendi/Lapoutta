package com.d4ti.lapoutta.apiHelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://localhost/lapoutta";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

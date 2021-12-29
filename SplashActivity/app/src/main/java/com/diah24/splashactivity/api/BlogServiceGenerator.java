package com.diah24.splashactivity.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogServiceGenerator {
    public static final String IP = "192.168.1.10";
    private static final String PORT = "8888";
    //private static final String BASE_URL = "http://" +IP+":"+PORT+"/blog-api/";
    private static final String BASE_URL = "https://ublmobilekmmi.web.id/kmmi_k1/";
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit = builder.build();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(
            Class<S> seriveClass) {
        if(!httpClient.interceptors().contains(logging)){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(seriveClass);
    }
}

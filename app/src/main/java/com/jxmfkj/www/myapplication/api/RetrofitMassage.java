package com.jxmfkj.www.myapplication.api;

import okhttp3.OkHttpClient;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Retrofit;

public class RetrofitMassage {
    private static RetrofitMassage helper;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public static RetrofitMassage getInstance() {
        if (helper == null) {
            synchronized (RetrofitMassage.class) {
                if (helper == null) {
                    helper = new RetrofitMassage();
                }
            }
        }
        return helper;
    }

    private RetrofitMassage() {
        OkHttpClient.Builder builder =new OkHttpClient.Builder();

    }
}

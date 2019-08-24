package com.jxmfkj.www.myapplication.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager manager;
    private Retrofit retrofit;
    private OkHttpClient mOkhttpClient;

    //饿汉式
    public static RetrofitManager getInstance() {
        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    private RetrofitManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor LoginIntenrceptor = new HttpLoggingInterceptor();
        LoginIntenrceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(LoginIntenrceptor);
        builder.connectTimeout(3, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        mOkhttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wananzhuo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkhttpClient)
                .build();

    }

    public <T> T create(Class<T> cls) {
        if (retrofit == null) {
            throw new NullPointerException(getClass().getSimpleName() + "retrofit is null");
        }
        return retrofit.create(cls);
    }
}

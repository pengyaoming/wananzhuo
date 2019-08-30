package com.jxmfkj.www.myapplication.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUitl {
    private static RetrofitUitl retrofitUitl;
    private static ApiServer apiService;
    private Retrofit mRetrofit;
    private RetrofitUitl(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build();
         mRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = mRetrofit.create(ApiServer.class);
    }
    public static RetrofitUitl getInstance(){
        if (retrofitUitl ==null){
            synchronized (RetrofitUitl.class){
                if (retrofitUitl == null)
                    retrofitUitl = new RetrofitUitl();
            }
        }
        return retrofitUitl;
    }
    public ApiServer Api(){
        return apiService;
    }

    public <T> T create(Class<T> cls) {
        if (mRetrofit == null) {
            throw new NullPointerException(getClass().getSimpleName() + " retrofit is null");
        }
        return mRetrofit.create(cls);
    }
}

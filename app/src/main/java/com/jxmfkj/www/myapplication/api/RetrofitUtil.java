package com.jxmfkj.www.myapplication.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private Context mContext;
    private static RetrofitUtil instance;
    private Retrofit mRetrofit;

    //防止context内存泄漏
    private RetrofitUtil(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        //Okhttp拦截器，拦截Okhttp的数据
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SaveCookiesInterceptor(mContext))
                .addInterceptor(new AddCookiesInterceptor(mContext))
                .addNetworkInterceptor(loggingInterceptor)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    //DCL模式
    public static RetrofitUtil getInstance(Context mContext) {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil(mContext);
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> cls) {
        if (mRetrofit == null) {
            throw new NullPointerException(getClass().getSimpleName());
        }
        return mRetrofit.create(cls);
    }
}

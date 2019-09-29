package com.jxmfkj.www.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static synchronized Context getContext() {
        return context;
    }
}

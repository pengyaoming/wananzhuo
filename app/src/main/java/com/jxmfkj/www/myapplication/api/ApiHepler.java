package com.jxmfkj.www.myapplication.api;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class ApiHepler {
    public static ApiAserver getService() {
        return RetrofitManager.getInstance().create(ApiAserver.class);
    }

}

package com.jxmfkj.www.myapplication.api;

import com.gutils.retrofit2.GSubscribeManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class ApiHepler {
    public static ApiAserver getService() {
        return RetrofitManager.getInstance().create(ApiAserver.class);
    }
    private static <T> Subscription getSubscription(Observable<T> mObservable, Observer<T> mObserver) {
        return GSubscribeManager.CustomSendSubScribe(mObservable, mObserver);
    }



    /**
     * 注册
     */
    public static Subscription postregister(String username, String password, String repassword, Observer<WrapperRspEntity> Observer) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username + "");
        map.put("password", password + "");
        map.put("repassword", repassword + "");
        return getSubscription(getService().getRegister(map),Observer);
    }

}

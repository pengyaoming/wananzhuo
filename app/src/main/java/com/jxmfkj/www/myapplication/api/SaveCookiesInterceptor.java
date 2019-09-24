package com.jxmfkj.www.myapplication.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookiesInterceptor implements Interceptor {
    private static final String COOKIE_PEEF = "cookie_prefs";
    private Context mContext;

    protected SaveCookiesInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //set_cookie可能为多个
        if (!response.headers("set_cookie").isEmpty()) {
            List<String> cookies = response.headers("set_cookie");
            String cookie = encodeCookie(cookies);
            saveCookie(request.url().toString(), request.url().host(), cookie);
        }
        return response;
    }

    /**
     * 整合cookie为唯一字符串
     *
     * @param cookie
     * @return
     */
    private String encodeCookie(List<String> cookie) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookies : cookie) {
            String[] arr = cookies.split(",");
            for (String s : arr) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }
        for (String cookies : set) {
            sb.append(cookies).append(",");
        }
        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }

    /**
     * 吧cookie保存到本地
     * @param url
     * @param domain
     * @param cookie
     */
    private void saveCookie(String url,String domain,String cookie){
        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PEEF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (TextUtils.isEmpty(url)){
            throw  new NullPointerException("url is null");
        }else {
            editor.putString(url,cookie);
        }
        if (!TextUtils.isEmpty(domain)){
            editor.putString(domain,cookie);
        }
        editor.apply();
    }

    /**
     * 清除本地cookie
     * @param context
     */
    public static void clearCookie(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKIE_PEEF,Context.MODE_PRIVATE);
        sharedPreferences.edit().apply();
}

}

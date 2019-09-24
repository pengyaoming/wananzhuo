package com.jxmfkj.www.myapplication.base;

import android.content.Intent;

public interface BaseView {
    /**
     * Toast
     * @param message
     */
    void showMessage(String message);

    /**
     *跳转Activity
     * @param intent
     */
    void luanchActivity(Intent intent);

    /**
     * 杀死自己
     */
    void FFinish();
}

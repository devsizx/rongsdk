package com.rongmzw.frame.sdk.api;

import android.app.Activity;

import com.rongmzw.frame.sdk.callback.HttpCallback;

/**
 * Created by different_loyal on 2017/12/28.
 */

public interface RongSdkRequestApi {
    /**
     * 初始化
     */
    void initRequest(Activity activity, HttpCallback initHttpCallback);

    /**
     * 登录
     */
    void loginRequest();

    /**
     * 支付
     */
    void payRequest();
}

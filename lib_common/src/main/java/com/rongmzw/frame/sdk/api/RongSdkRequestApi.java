package com.rongmzw.frame.sdk.api;

import android.app.Activity;

import com.rongmzw.frame.sdk.callback.RongHttpCallback;

/**
 * Created by different_loyal on 2017/12/28.
 */

public interface RongSdkRequestApi {
    /**
     * 初始化
     */
    void initRequest(Activity activity, RongHttpCallback initHttpCallback);

    /**
     * 登录
     */
    void loginRequest(Activity activity, String userInfo, RongHttpCallback initHttpCallback);

    /**
     * 支付
     */
    void payRequest(Activity activity, String orderInfo, RongHttpCallback payHttpCallback);
}

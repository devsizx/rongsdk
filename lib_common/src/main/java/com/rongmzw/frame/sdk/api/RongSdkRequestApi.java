package com.rongmzw.frame.sdk.api;

/**
 * Created by different_loyal on 2017/12/28.
 */

public interface RongSdkRequestApi {
    /**
     * 初始化
     */
    void initRequest();

    /**
     * 登录
     */
    void loginRequest();

    /**
     * 支付
     */
    void payRequest();
}

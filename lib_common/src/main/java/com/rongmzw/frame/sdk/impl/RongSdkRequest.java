package com.rongmzw.frame.sdk.impl;

import android.app.Activity;

import com.google.gson.Gson;
import com.muzhiwan.sdk.core.MzwSdkController;
import com.rongmzw.frame.sdk.api.RongSdkRequestApi;
import com.rongmzw.frame.sdk.callback.RongHttpCallback;
import com.rongmzw.frame.sdk.domain.http.InitResponse;
import com.rongmzw.frame.sdk.domain.http.LoginResponse;
import com.rongmzw.frame.sdk.domain.http.PayResponse;
import com.rongmzw.frame.sdk.utils.HttpUtils;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class RongSdkRequest implements RongSdkRequestApi {
    protected Activity gameActivity;
    protected MzwSdkController mzwSdkController = MzwSdkController.getInstance();
    protected Gson gson = new Gson();
    protected InitResponse initResponse = null;
    protected LoginResponse loginResponse = null;
    protected PayResponse payResponse = null;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_HORIZONTAL = 2;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_VERTICAL = 1;

    @Override
    public void initRequest(Activity activity, RongHttpCallback initHttpCallback) {
        HttpUtils.initRequest(activity, initHttpCallback);
    }

    @Override
    public void loginRequest(Activity activity, String userInfo, RongHttpCallback loginHttpCallback) {
        HttpUtils.loginRequest(activity, userInfo, loginHttpCallback);
    }

    @Override
    public void payRequest(Activity activity, String orderInfo, RongHttpCallback payHttpCallback) {
        HttpUtils.payRequest(activity, orderInfo, payHttpCallback);
    }
}

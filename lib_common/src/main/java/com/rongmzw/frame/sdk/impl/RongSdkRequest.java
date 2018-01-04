package com.rongmzw.frame.sdk.impl;

import android.app.Activity;

import com.rongmzw.frame.sdk.api.RongSdkRequestApi;
import com.rongmzw.frame.sdk.callback.HttpCallback;
import com.rongmzw.frame.sdk.utils.HttpUtils;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class RongSdkRequest implements RongSdkRequestApi {
    @Override
    public void initRequest(Activity activity, HttpCallback initHttpCallback) {
        HttpUtils.initRequest(activity, initHttpCallback);
    }

    @Override
    public void loginRequest() {

    }

    @Override
    public void payRequest() {

    }
}

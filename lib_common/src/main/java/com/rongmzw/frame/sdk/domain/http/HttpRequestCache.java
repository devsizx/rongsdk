package com.rongmzw.frame.sdk.domain.http;

import android.app.Activity;

import com.rongmzw.frame.sdk.callback.RongHttpCallback;

import java.util.Map;

/**
 * Created by different_loyal on 2018/2/6.
 */

public class HttpRequestCache {
    public static final int REQUEST_TYPE_INIT = 0x01;
    public static final int REQUEST_TYPE_LOGIN = 0x02;
    public static final int REQUEST_TYPE_PAY = 0x03;
    private Activity activity;
    private String url;
    private Map<String, String> paramsMap;
    private RongHttpCallback callback;
    private int requestType;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public RongHttpCallback getCallback() {
        return callback;
    }

    public void setCallback(RongHttpCallback callback) {
        this.callback = callback;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}

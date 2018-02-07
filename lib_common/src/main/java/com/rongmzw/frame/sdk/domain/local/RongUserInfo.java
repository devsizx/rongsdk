package com.rongmzw.frame.sdk.domain.local;

/**
 * Created by different_loyal on 2018/2/6.
 */

public class RongUserInfo {
    private String cptoken;
    private String deviceId;
    private String requestUrl;

    public String getCptoken() {
        return cptoken;
    }

    public void setCptoken(String cptoken) {
        this.cptoken = cptoken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}

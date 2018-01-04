package com.rongmzw.frame.sdk.callback;

/**
 * Created by different_loyal on 2018/1/4.
 */

public interface HttpCallback {
    void onSuccess(String type, String msg);

    void onFailed(String type, String msg);
}

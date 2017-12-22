package com.rongmzw.frame.sdk.callback;

/**
 * Created by different_loyal on 2017/12/22.
 */

public interface RongCallback {
    int TYPE_LOGIN = 0x01;
    int TYPE_PAY = 0x02;
    int TYPE_STAPAY = 0x03;
    int TYPE_INIT = 0x04;
    int TYPE_EXITGAME = 0x05;
    void onResult(int type, int code, String result);
}

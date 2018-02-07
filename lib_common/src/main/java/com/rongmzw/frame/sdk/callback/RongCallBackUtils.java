package com.rongmzw.frame.sdk.callback;

/**
 * Created by different_loyal on 2018/1/8.
 */

public class RongCallBackUtils {
    public static RongCallback rongCallback;
    public static RongCallBackUtils instance;

    public static RongCallBackUtils getInstance() {
        synchronized (RongCallBackUtils.class) {
            if (instance == null) {
                instance = new RongCallBackUtils();
            }
        }
        return instance;
    }

    public static void initCallBack(int callBackCode, String msg) {
        RongCallBackUtils.getInstance().initCallback(callBackCode, msg);
    }

    public static void initSuccessCallBack(String msg) {
        initCallBack(RongCallback.CODE_SUCCESS, msg);
    }

    public static void initFailedCallBack(String msg) {
        initCallBack(RongCallback.CODE_FAILED, msg);
    }

    public static void loginCallBack(int callBackCode, String msg) {
        RongCallBackUtils.getInstance().loginCallback(callBackCode, msg);
    }

    public static void loginSuccessCallBack(String msg) {
        loginCallBack(RongCallback.CODE_SUCCESS, msg);
    }

    public static void loginFailedCallBack(String msg) {
        loginCallBack(RongCallback.CODE_FAILED, msg);
    }

    public static void logoutCallBack() {
        loginCallBack(RongCallback.CODE_LOGIN_LOGOUT, "logout ...");
    }

    public static void payCallBack(int callBackCode, String orderInfo) {
        RongCallBackUtils.getInstance().payCallback(callBackCode, orderInfo);
    }

    public static void paySuccessCallBack(String orderInfo) {
        payCallBack(RongCallback.CODE_SUCCESS, orderInfo);
    }

    public static void payFailedCallBack(String orderInfo) {
        payCallBack(RongCallback.CODE_FAILED, orderInfo);
    }

    public static void exitGameCallBack(int callBackCode, String msg) {
        RongCallBackUtils.getInstance().exitGameCallback(callBackCode, msg);
    }

    public static void exitGameSuccessCallBack(String msg) {
        exitGameCallBack(RongCallback.CODE_SUCCESS, msg);
    }

    public static void exitGameFailedCallBack(String msg) {
        exitGameCallBack(RongCallback.CODE_FAILED, msg);
    }

    private void initCallback(int callBackCode, String msg) {
        if (rongCallback != null) {
            rongCallback.onResult(RongCallback.TYPE_INIT, callBackCode, msg);
        }
    }

    private void loginCallback(int callBackCode, String msg) {
        if (rongCallback != null) {
            rongCallback.onResult(RongCallback.TYPE_LOGIN, callBackCode, msg);
        }
    }

    private void payCallback(int callBackCode, String orderInfo) {
        if (rongCallback != null) {
            rongCallback.onResult(RongCallback.TYPE_PAY, callBackCode, orderInfo);
        }
    }

    private void exitGameCallback(int callBackCode, String msg) {
        if (rongCallback != null) {
            rongCallback.onResult(RongCallback.TYPE_EXITGAME, callBackCode, msg);
        }
    }
}

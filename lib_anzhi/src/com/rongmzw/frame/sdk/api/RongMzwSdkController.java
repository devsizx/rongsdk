package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.anzhi.sdk.middle.manage.AnzhiSDK;
import com.anzhi.sdk.middle.manage.GameCallBack;
import com.anzhi.sdk.middle.util.MD5;
import com.rongmzw.frame.sdk.callback.RongMzwExitGameCallback;
import com.rongmzw.frame.sdk.callback.RongMzwInitCallback;
import com.rongmzw.frame.sdk.callback.RongMzwLoignCallback;
import com.rongmzw.frame.sdk.callback.RongMzwPayCallback;
import com.rongmzw.frame.sdk.callback.RongMzwStaPayCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongMzwOrder;
import com.rongmzw.frame.sdk.util.Des3Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongMzwSdkController implements RongMzwSdkApi {
    private static String TAG = RongMzwSdkController.class.getSimpleName();
    private String Appkey = "1378375366Az26xatNyDOD5EM6D2ys";// SDK 初始化参数
    private String AppSecret = "ug2KMdLi2JSr4naOE48XmL3h";
    private Activity gameActivity;
    private AnzhiSDK anzhiSDK = AnzhiSDK.getInstance();
    private RongMzwInitCallback rongMzwInitCallback;
    private RongMzwLoignCallback rongMzwLoignCallback;
    private RongMzwPayCallback rongMzwPayCallback;
    private RongMzwStaPayCallback rongMzwStaPayCallback;
    private RongMzwExitGameCallback rongMzwExitGameCallback;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_HORIZONTAL = 2;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_VERTICAL = 1;
    private boolean hasLoopered;
    //anzhiSDK对应的回调函数
    private GameCallBack callback = new GameCallBack() {
        @Override
        public void callBack(final int type, final String result) {
            Log.i("Anzhi_SDK_TEST", "code: " + type + ", result: " + result);
            Looper.prepare();
            switch (type) {
                case GameCallBack.SDK_TYPE_INIT: // 初始化操作
                    rongMzwInitCallback.onResult(1, result);
                    anzhiSDK.addPop(gameActivity);
                    break;
                case GameCallBack.SDK_TYPE_LOGIN: // 登录操作
                    try {
                        JSONObject json = new JSONObject(result);
                        if (json.optInt("code") == 200) { // 登录成功
                            String cptoken = json.optString("cptoken");
                            String deviceId = json.optString("deviceId");
                            String requestUrl = json.optString("requestUrl");
                            //通过cptoken\deviceId\requestUrl获取anzhi对应的uid
                            Log.e("anzhi", "cptoken==" + cptoken + ";deviceId==" + deviceId + ";requestUrl==" + requestUrl);
                            rongMzwLoignCallback.onResult(1, "login success");
                        } else { // 登录失败
                            String desc = json.optString("code_desc");
                            rongMzwLoignCallback.onResult(0, desc);
                        }
                    } catch (JSONException e) {
                    }
                    break;
                case GameCallBack.SDK_TYPE_LOGOUT: // 注销登录操作
                    rongMzwLoignCallback.onResult(6, "登出操作");
                    break;
                case GameCallBack.SDK_TYPE_PAY:
                    rongMzwPayCallback.onResult(type, result);
                    break;
                case GameCallBack.SDK_TYPE_EXIT_GAME: // 退出游戏操作
                    rongMzwExitGameCallback.onResult(1, "确认退出游戏");
                    break;
                case GameCallBack.SDK_TYPE_CANCEL_EXIT_GAME: // 取消退出游戏操作
                    rongMzwExitGameCallback.onResult(0, "取消退出游戏");
                    break;
            }
        }
    };

    private static class MzwSdkControllerHolder {
        private static final RongMzwSdkController INSTANCE = new RongMzwSdkController();
    }

    private RongMzwSdkController() {
    }

    /**
     * 获取Controller 实例
     *
     * @return 控制器实例
     */
    public static RongMzwSdkController getInstance() {
        return MzwSdkControllerHolder.INSTANCE;
    }

    @Override
    public void callInit(Activity gameActivity, int screenOrientation, RongMzwInitCallback initCallback) {
        this.gameActivity = gameActivity;
        this.rongMzwInitCallback = initCallback;
        anzhiSDK.init(gameActivity, Appkey, AppSecret, callback);
    }

    @Override
    public void callLogin(RongMzwLoignCallback loginCallback) {
        this.rongMzwLoignCallback = loginCallback;
        anzhiSDK.login(this.gameActivity);
    }

    @Override
    public void callPay(RongMzwOrder rongMzwOrder, RongMzwPayCallback payCallback) {
        this.rongMzwPayCallback = payCallback;
        JSONObject json = new JSONObject();
        try {
            // 游戏方生成的订单号,可以作为与安智订单进行关联
            json.put("amount", rongMzwOrder.getProductPrice() * 100);// 支付金额(单位：分)
            json.put("productCode", rongMzwOrder.getProductId());// 游戏方商品代码
            json.put("productName", rongMzwOrder.getProductName());// 游戏方商品名称
            json.put("cpCustomInfo", rongMzwOrder.getProductDesc());// 游戏方自定义数据
            json.put("productCount", 1);// 商品数量
            json.put("cpOrderId", "anzhi_" + System.currentTimeMillis());
            json.put("cpOrderTime", System.currentTimeMillis());// 下单时间
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = json.toString();
        anzhiSDK.pay(Des3Util.encrypt(data, AppSecret), MD5.encodeToString(AppSecret));
    }

    @Override
    public void callStaPay(RongMzwStaPayCallback staPayCallback) {

    }

    @Override
    public void callLogout() {
        anzhiSDK.logout();
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        anzhiSDK.subGameInfo(com.alibaba.fastjson.JSONObject.toJSONString(gameInfo));
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {
        anzhiSDK.onNewIntentInvoked(intent);
    }

    @Override
    public void callOnResumeInvoked() {
        anzhiSDK.onResumeInvoked();
    }

    @Override
    public void callOnStartInvoked() {
        anzhiSDK.onStartInvoked();
    }

    @Override
    public void callOnPauseInvoked() {
        anzhiSDK.onPauseInvoked();
    }

    @Override
    public void callOnStopInvoked() {
        anzhiSDK.onStopInvoked();
    }

    @Override
    public void callOnDestoryInvoked() {
        anzhiSDK.onDestoryInvoked();
    }

    @Override
    public void callExitGame(RongMzwExitGameCallback exitGameCallback) {
        this.rongMzwExitGameCallback = exitGameCallback;
        anzhiSDK.exitGame(this.gameActivity);
    }

}

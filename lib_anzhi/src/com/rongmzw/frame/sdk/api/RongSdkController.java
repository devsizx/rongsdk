package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.anzhi.sdk.middle.manage.AnzhiSDK;
import com.anzhi.sdk.middle.manage.GameCallBack;
import com.anzhi.sdk.middle.util.MD5;
import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongOrder;
import com.rongmzw.frame.sdk.util.Des3Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongSdkController implements RongSdkApi {
    private static String TAG = RongSdkController.class.getSimpleName();
    private String Appkey = "1378375366Az26xatNyDOD5EM6D2ys";// SDK 初始化参数
    private String AppSecret = "ug2KMdLi2JSr4naOE48XmL3h";
    private Activity gameActivity;
    private AnzhiSDK anzhiSDK = AnzhiSDK.getInstance();
    private RongCallback rongCallback;
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
            Log.i(TAG, "anzhi callback code: " + type + ", result: " + result);
            switch (type) {
                case GameCallBack.SDK_TYPE_INIT: // 初始化操作
                    if (!hasLoopered) {
                        Looper.prepare();
                        hasLoopered = true;
                    }
                    rongCallback.onResult(RongCallback.TYPE_INIT, 1, result);
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
                            Log.e(TAG, "anzhi cptoken==" + cptoken + ";deviceId==" + deviceId + ";requestUrl==" + requestUrl);
                            rongCallback.onResult(RongCallback.TYPE_LOGIN, 1, "login success");
                        } else { // 登录失败
                            String desc = json.optString("code_desc");
                            rongCallback.onResult(RongCallback.TYPE_LOGIN, 0, desc);
                        }
                    } catch (JSONException e) {
                    }
                    break;
                case GameCallBack.SDK_TYPE_LOGOUT: // 注销登录操作
                    rongCallback.onResult(RongCallback.TYPE_LOGIN, 6, "logout success");
                    break;
                case GameCallBack.SDK_TYPE_PAY:
                    rongCallback.onResult(RongCallback.TYPE_PAY, type, result);
                    break;
                case GameCallBack.SDK_TYPE_CANCEL_PAY:
                    rongCallback.onResult(RongCallback.TYPE_PAY, type, result);
                    break;
                case GameCallBack.SDK_TYPE_EXIT_GAME: // 退出游戏操作
                    rongCallback.onResult(RongCallback.TYPE_EXITGAME, 1, "exitgame success");
                    break;
                case GameCallBack.SDK_TYPE_CANCEL_EXIT_GAME: // 取消退出游戏操作
                    rongCallback.onResult(RongCallback.TYPE_EXITGAME, 0, "exitgame cancel");
                    break;
            }
        }
    };

    private static class MzwSdkControllerHolder {
        private static final RongSdkController INSTANCE = new RongSdkController();
    }

    private RongSdkController() {
    }

    /**
     * 获取Controller 实例
     *
     * @return 控制器实例
     */
    public static RongSdkController getInstance() {
        return MzwSdkControllerHolder.INSTANCE;
    }

    @Override
    public void callInit(Activity gameActivity, int screenOrientation, RongCallback rongCallback) {
        Log.e(TAG, "anzhi callInit......");
        this.gameActivity = gameActivity;
        this.rongCallback = rongCallback;
        anzhiSDK.init(gameActivity, Appkey, AppSecret, callback);
    }

    @Override
    public void callLogin() {
        Log.e(TAG, "anzhi callLogin......");
        anzhiSDK.login(this.gameActivity);
    }

    @Override
    public void callPay(RongOrder rongMzwOrder) {
        Log.e(TAG, "anzhi callPay......rongMzwOrder--->" + rongMzwOrder);
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
    public void callStaPay() {
        Log.e(TAG, "anzhi callStaPay......");

    }

    @Override
    public void callLogout() {
        Log.e(TAG, "anzhi callLogout......");
        anzhiSDK.logout();
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        Log.e(TAG, "anzhi callSubGameInfo.....gameInfo--->" + gameInfo);
        anzhiSDK.subGameInfo(com.alibaba.fastjson.JSONObject.toJSONString(gameInfo));
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {
        Log.e(TAG, "anzhi callOnNewIntentInvoked......");
        anzhiSDK.onNewIntentInvoked(intent);
    }

    @Override
    public void callOnResumeInvoked() {
        Log.e(TAG, "anzhi callOnResumeInvoked......");
        anzhiSDK.onResumeInvoked();
    }

    @Override
    public void callOnStartInvoked() {
        Log.e(TAG, "anzhi callOnStartInvoked......");
        anzhiSDK.onStartInvoked();
    }

    @Override
    public void callOnPauseInvoked() {
        Log.e(TAG, "anzhi callOnPauseInvoked......");
        anzhiSDK.onPauseInvoked();
    }

    @Override
    public void callOnStopInvoked() {
        Log.e(TAG, "anzhi callOnStopInvoked......");
        anzhiSDK.onStopInvoked();
    }

    @Override
    public void callOnDestoryInvoked() {
        Log.e(TAG, "anzhi callOnDestoryInvoked......");
        anzhiSDK.onDestoryInvoked();
    }

    @Override
    public void callExitGame() {
        Log.e(TAG, "anzhi callExitGame......");
        anzhiSDK.exitGame(this.gameActivity);
    }

}

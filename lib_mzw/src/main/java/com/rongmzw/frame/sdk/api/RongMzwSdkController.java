package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.muzhiwan.sdk.core.MzwSdkController;
import com.muzhiwan.sdk.core.callback.MzwExitGameCallBack;
import com.muzhiwan.sdk.core.callback.MzwInitCallback;
import com.muzhiwan.sdk.core.callback.MzwLoignCallback;
import com.muzhiwan.sdk.core.callback.MzwPayCallback;
import com.muzhiwan.sdk.core.callback.MzwStaPayCallback;
import com.muzhiwan.sdk.service.MzwOrder;
import com.rongmzw.frame.sdk.callback.RongMzwExitGameCallback;
import com.rongmzw.frame.sdk.callback.RongMzwInitCallback;
import com.rongmzw.frame.sdk.callback.RongMzwLoignCallback;
import com.rongmzw.frame.sdk.callback.RongMzwPayCallback;
import com.rongmzw.frame.sdk.callback.RongMzwStaPayCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongMzwOrder;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongMzwSdkController implements RongMzwSdkApi {
    private static String TAG = RongMzwSdkController.class.getSimpleName();
    private MzwSdkController mzwSdkController = MzwSdkController.getInstance();
    private Activity gameActivity;
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
        Log.e(TAG, "mzw callInit......");
        this.gameActivity = gameActivity;
        this.rongMzwInitCallback = initCallback;
        mzwSdkController.init(this.gameActivity, screenOrientation, new MzwInitCallback() {
            @Override
            public void onResult(int i, String s) {
                rongMzwInitCallback.onResult(i, s);
            }
        });
    }

    @Override
    public void callLogin(RongMzwLoignCallback loginCallback) {
        Log.e(TAG, "mzw callLogin......");
        this.rongMzwLoignCallback = loginCallback;
        mzwSdkController.doLogin(new MzwLoignCallback() {
            @Override
            public void onResult(int i, String s) {
                rongMzwLoignCallback.onResult(i, s);
            }
        });
    }

    @Override
    public void callPay(RongMzwOrder rongMzwOrder, RongMzwPayCallback payCallback) {
        Log.e(TAG, "mzw callPay......rongMzwOrder--->" + rongMzwOrder);
        this.rongMzwPayCallback = payCallback;
        MzwOrder mzwOrder = new MzwOrder();
        mzwOrder.setMoney(rongMzwOrder.getProductPrice());
        mzwOrder.setProductdesc(rongMzwOrder.getProductDesc());
        mzwOrder.setProductid(rongMzwOrder.getProductId());
        mzwOrder.setProductname(rongMzwOrder.getProductName());
        mzwSdkController.doPay(mzwOrder, new MzwPayCallback() {
            @Override
            public void onResult(int i, MzwOrder mzwOrder) {
                rongMzwPayCallback.onResult(i, mzwOrder.toString());
            }
        });
    }

    @Override
    public void callStaPay(RongMzwStaPayCallback staPayCallback) {
        Log.e(TAG, "mzw callStaPay......");
        this.rongMzwStaPayCallback = staPayCallback;
        mzwSdkController.doStaPay("rtp4pmx7", "syotepdd", "奖励女朋友一个", new MzwStaPayCallback() {
            @Override
            public void onResult(int code, String msg) {
                rongMzwStaPayCallback.onResult(code, msg);
            }
        });
    }

    @Override
    public void callLogout() {
        Log.e(TAG, "mzw callLogout......");
        mzwSdkController.doLogout();
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        Log.e(TAG, "mzw callSubGameInfo......gameInfo---->" + gameInfo.toString());
        mzwSdkController.subGameInfo(gameInfo.toString());
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {
        Log.e(TAG, "mzw callOnNewIntentInvoked......");
    }

    @Override
    public void callOnResumeInvoked() {
        Log.e(TAG, "mzw callOnResumeInvoked......");
    }

    @Override
    public void callOnStartInvoked() {
        Log.e(TAG, "mzw callOnStartInvoked......");
    }

    @Override
    public void callOnPauseInvoked() {
        Log.e(TAG, "mzw callOnPauseInvoked......");
    }

    @Override
    public void callOnStopInvoked() {
        Log.e(TAG, "mzw callOnStopInvoked......");
    }

    @Override
    public void callOnDestoryInvoked() {
        Log.e(TAG, "mzw callOnDestoryInvoked......");
        mzwSdkController.destory();
    }

    @Override
    public void callExitGame(RongMzwExitGameCallback exitGameCallback) {
        Log.e(TAG, "mzw callExitGame......");
        this.rongMzwExitGameCallback = exitGameCallback;
        mzwSdkController.exitGame(new MzwExitGameCallBack() {
            @Override
            public void onResult(int i, String s) {
                rongMzwExitGameCallback.onResult(i, s);
            }
        });
    }
}

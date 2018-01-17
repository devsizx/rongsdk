package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.muzhiwan.sdk.core.MzwSdkController;
import com.muzhiwan.sdk.core.callback.MzwInitCallback;
import com.muzhiwan.sdk.core.callback.MzwLoignCallback;
import com.muzhiwan.sdk.core.callback.MzwPayCallback;
import com.muzhiwan.sdk.core.callback.MzwStaPayCallback;
import com.muzhiwan.sdk.service.MzwOrder;
import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.domain.http.InitResponse;
import com.rongmzw.frame.sdk.domain.local.RongGameInfo;
import com.rongmzw.frame.sdk.domain.local.RongOrder;
import com.rongmzw.frame.sdk.impl.RongSdkRequest;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongSdkController extends RongSdkRequest implements RongSdkApi {
    private static String TAG = RongSdkController.class.getSimpleName();
    private MzwSdkController mzwSdkController = MzwSdkController.getInstance();
    private Activity gameActivity;
    private RongCallback rongCallback;
    private InitResponse initResponse = null;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_HORIZONTAL = 2;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_VERTICAL = 1;

    private static class RongSdkControllerHolder {
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
        return RongSdkControllerHolder.INSTANCE;
    }

    @Override
    public void callInit(final Activity gameActivity, final int screenOrientation, final RongCallback rongCallback) {
        Log.e(TAG, "mzw callInit......");
        this.gameActivity = gameActivity;
        this.rongCallback = rongCallback;
        mzwSdkController.init(gameActivity, screenOrientation, new MzwInitCallback() {
            @Override
            public void onResult(int i, String s) {
                rongCallback.onResult(RongCallback.TYPE_INIT, i, s);
            }
        });
    }

    @Override
    public void callLogin() {
        Log.e(TAG, "mzw callLogin......");
        mzwSdkController.doLogin(new MzwLoignCallback() {
            @Override
            public void onResult(int i, String s) {
                rongCallback.onResult(RongCallback.TYPE_LOGIN, i, s);
            }
        });
    }

    @Override
    public void callPay(RongOrder rongMzwOrder) {
        Log.e(TAG, "mzw callPay......rongMzwOrder--->" + rongMzwOrder);
        MzwOrder mzwOrder = new MzwOrder();
        mzwOrder.setMoney(rongMzwOrder.getProductPrice());
        mzwOrder.setProductdesc(rongMzwOrder.getProductDesc());
        mzwOrder.setProductid(rongMzwOrder.getProductId());
        mzwOrder.setProductname(rongMzwOrder.getProductName());
        mzwSdkController.doPay(mzwOrder, new MzwPayCallback() {
            @Override
            public void onResult(int i, MzwOrder mzwOrder) {
                rongCallback.onResult(RongCallback.TYPE_PAY, i, mzwOrder.toString());
            }
        });
    }

    @Override
    public void callStaPay() {
        Log.e(TAG, "mzw callStaPay......");
        mzwSdkController.doStaPay("rtp4pmx7", "syotepdd", "奖励女朋友一个", new MzwStaPayCallback() {
            @Override
            public void onResult(int code, String msg) {
                rongCallback.onResult(RongCallback.TYPE_STAPAY, code, msg);
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
    public void callExitGame() {
        Log.e(TAG, "mzw callExitGame......");
        rongCallback.onResult(RongCallback.TYPE_EXITGAME, 1, "exitGame success......");
//        mzwSdkController.exitGame(new MzwExitGameCallBack() {
//            @Override
//            public void onResult(int i, String s) {
//                rongMzwExitGameCallback.onResult(i, s);
//            }
//        });
    }
}

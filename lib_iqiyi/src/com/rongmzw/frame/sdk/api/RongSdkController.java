package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.iqiyi.sdk.listener.LoginListener;
import com.iqiyi.sdk.listener.PayListener;
import com.iqiyi.sdk.platform.GamePlatform;
import com.iqiyi.sdk.platform.GamePlatformInitListener;
import com.iqiyi.sdk.platform.GameSDKResultCode;
import com.iqiyi.sdk.platform.GameUser;
import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongOrder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongSdkController implements RongSdkApi {
    private static String TAG = RongSdkController.class.getSimpleName();
    private Activity gameActivity;
    private String gameId = "10003";
    private GamePlatform iqiyiGamePlatform = GamePlatform.getInstance();
    private RongCallback rongCallback;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_HORIZONTAL = 2;
    /**
     * 游戏屏幕方向设置，由厂商传入，ORIENTATION_HORIZONTAL代表横屏，ORIENTATION_VERTICAL代表竖屏
     **/
    public static final int ORIENTATION_VERTICAL = 1;

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
    public void callInit(final Activity gameActivity, int screenOrientation, final RongCallback rongCallback) {
        Log.e(TAG, "iqiyi callInit......");
        this.gameActivity = gameActivity;
        this.rongCallback = rongCallback;
        iqiyiGamePlatform.initPlatform(this.gameActivity, gameId, new GamePlatformInitListener() {
            @Override
            public void onSuccess() {
                rongCallback.onResult(RongCallback.TYPE_INIT, 1, "init success");
                iqiyiGamePlatform.addLoginListener(new LoginListener() {
                    @Override
                    public void loginResult(int i, GameUser gameUser) {
                        if (i == GameSDKResultCode.SUCCESSLOGIN && gameUser != null) {
                            iqiyiGamePlatform.initSliderBar(gameActivity);
                            System.out.println("uid : " + gameUser.uid);
                            System.out.println("time : " + gameUser.timestamp);
                            System.out.println("sign : " + gameUser.sign);
                            getUserType();
                            //游戏方使用我们提供的登录key按照md5(uid + “&” + time + “&” + key)这个方式计算出一个sign与从登录接口回调中接收的sign进行对比
                            rongCallback.onResult(RongCallback.TYPE_LOGIN, 1, "login success");
                        } else {
                            rongCallback.onResult(RongCallback.TYPE_LOGIN, 0, "login failed");
                        }
                    }

                    @Override
                    public void logout() {
                        rongCallback.onResult(RongCallback.TYPE_LOGIN, 6, "logout success");
                    }

                    @Override
                    public void exitGame() {
                        rongCallback.onResult(RongCallback.TYPE_EXITGAME, 1, "exit game success");
                        gameActivity.finish();
                        System.exit(0);
                    }
                });

                iqiyiGamePlatform.addPaymentListener(new PayListener() {
                    @Override
                    public void leavePlatform() {
                        //离开SDK支付平台
                    }

                    @Override
                    public void paymentResult(int i) {
                        if (i == GameSDKResultCode.SUCCESSPAYMENT) {
                            rongCallback.onResult(RongCallback.TYPE_PAY, 1, "pay success");
                        } else {
                            rongCallback.onResult(RongCallback.TYPE_PAY, 0, "pay failed");
                        }
                    }
                });
            }

            @Override
            public void onFail(String s) {
                rongCallback.onResult(RongCallback.TYPE_INIT, 0, "init failed because " + s);
            }
        });
    }

    @Override
    public void callLogin() {
        Log.e(TAG, "iqiyi callLogin......");
        iqiyiGamePlatform.iqiyiUserLogin(gameActivity);
    }

    @Override
    public void callPay(RongOrder rongMzwOrder) {
        Log.e(TAG, "iqiyi callPay......");
        iqiyiGamePlatform.iqiyiPayment(gameActivity, rongMzwOrder.getProductPrice(), rongMzwOrder.getRoleId(), rongMzwOrder.getServerId(), rongMzwOrder.getUserData());
    }

    @Override
    public void callStaPay() {
        Log.e(TAG, "iqiyi callStaPay......");
    }

    @Override
    public void callLogout() {
        Log.e(TAG, "iqiyi callLogout......");
        iqiyiGamePlatform.iqiyiUserLogout(gameActivity);
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        Log.e(TAG, "iqiyi callSubGameInfo......");
        iqiyiGamePlatform.enterGame(gameActivity, gameInfo.getGameAreaId());
        iqiyiGamePlatform.createRole(gameActivity, gameInfo.getRoleId());
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {
        Log.e(TAG, "iqiyi callOnNewIntentInvoked......");
    }

    @Override
    public void callOnResumeInvoked() {
        Log.e(TAG, "iqiyi callOnResumeInvoked......");
    }

    @Override
    public void callOnStartInvoked() {
        Log.e(TAG, "iqiyi callOnStartInvoked......");
    }

    @Override
    public void callOnPauseInvoked() {
        Log.e(TAG, "iqiyi callOnPauseInvoked......");
    }

    @Override
    public void callOnStopInvoked() {
        Log.e(TAG, "iqiyi callOnStopInvoked......");
    }

    @Override
    public void callOnDestoryInvoked() {
        Log.e(TAG, "iqiyi callOnDestoryInvoked......");
    }

    @Override
    public void callExitGame() {
        Log.e(TAG, "iqiyi callExitGame......");
        iqiyiGamePlatform.iqiyiUserLogout(this.gameActivity);
    }

    private void getUserType() {
        try {
            JSONObject json = iqiyiGamePlatform.getQIYIType();
            if (json != null) {
                String type = json.getString("type"); // 0：非会员， 1：会员
                int level = json.getInt("level"); // 会员等级
                String is_game_vip = json.getString("is_game_vip");
                String s = "province:" + json.getString("province") + ",city:"
                        + json.getString("city") + ",gender:"
                        + json.getString("gender") + ",icon:"
                        + json.getString("icon");
                Log.i("SDKPlatfrom", "VIP : " + type + ", levele : " + level + ", is_game_vip " + is_game_vip);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

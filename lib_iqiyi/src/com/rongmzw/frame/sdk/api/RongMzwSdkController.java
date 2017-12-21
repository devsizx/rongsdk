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
import com.rongmzw.frame.sdk.callback.RongMzwExitGameCallback;
import com.rongmzw.frame.sdk.callback.RongMzwInitCallback;
import com.rongmzw.frame.sdk.callback.RongMzwLoignCallback;
import com.rongmzw.frame.sdk.callback.RongMzwPayCallback;
import com.rongmzw.frame.sdk.callback.RongMzwStaPayCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongMzwOrder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongMzwSdkController implements RongMzwSdkApi{
    private static String TAG = RongMzwSdkController.class.getSimpleName();
    private Activity gameActivity;
    private String gameId = "10003";
    private GamePlatform iqiyiGamePlatform = GamePlatform.getInstance();
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
    public void callInit(final Activity gameActivity, int screenOrientation, RongMzwInitCallback initCallback) {
        this.gameActivity = gameActivity;
        this.rongMzwInitCallback = initCallback;
        iqiyiGamePlatform.initPlatform(this.gameActivity, gameId, new GamePlatformInitListener() {
            @Override
            public void onSuccess() {
                rongMzwInitCallback.onResult(1, "init success");
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
                            rongMzwLoignCallback.onResult(1, "login success");
                        } else {
                            rongMzwLoignCallback.onResult(0, "login failed");
                        }
                    }

                    @Override
                    public void logout() {
                        rongMzwLoignCallback.onResult(6, "logout success");
                    }

                    @Override
                    public void exitGame() {
                        rongMzwExitGameCallback.onResult(1, "exit game success");
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
                            rongMzwPayCallback.onResult(1, "pay success");
                        } else {
                            rongMzwPayCallback.onResult(0, "pay failed");
                        }
                    }
                });
            }

            @Override
            public void onFail(String s) {
                rongMzwInitCallback.onResult(0, "init failed because " + s);
            }
        });
    }

    @Override
    public void callLogin(RongMzwLoignCallback loginCallback) {
        this.rongMzwLoignCallback = loginCallback;
        iqiyiGamePlatform.iqiyiUserLogin(gameActivity);
    }

    @Override
    public void callPay(RongMzwOrder rongMzwOrder, RongMzwPayCallback payCallback) {
        this.rongMzwPayCallback = payCallback;
        iqiyiGamePlatform.iqiyiPayment(gameActivity, rongMzwOrder.getProductPrice(), rongMzwOrder.getRoleId(), rongMzwOrder.getServerId(), rongMzwOrder.getUserData());
    }

    @Override
    public void callStaPay(RongMzwStaPayCallback staPayCallback) {

    }

    @Override
    public void callLogout() {
        iqiyiGamePlatform.iqiyiUserLogout(gameActivity);
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        iqiyiGamePlatform.enterGame(gameActivity, gameInfo.getGameAreaId());
        iqiyiGamePlatform.createRole(gameActivity, gameInfo.getRoleId());
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {

    }

    @Override
    public void callOnResumeInvoked() {

    }

    @Override
    public void callOnStartInvoked() {

    }

    @Override
    public void callOnPauseInvoked() {

    }

    @Override
    public void callOnStopInvoked() {

    }

    @Override
    public void callOnDestoryInvoked() {

    }

    @Override
    public void callExitGame(RongMzwExitGameCallback exitGameCallback) {
        this.rongMzwExitGameCallback = exitGameCallback;
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

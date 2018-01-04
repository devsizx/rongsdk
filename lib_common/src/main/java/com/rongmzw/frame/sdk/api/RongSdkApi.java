package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;

import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.domain.local.RongGameInfo;
import com.rongmzw.frame.sdk.domain.local.RongOrder;

/**
 * Created by Administrator on 2017/12/19.
 */
public interface RongSdkApi {
    /**
     * 初始化接口
     *
     * @param gameActivity      游戏主Activity
     * @param screenOrientation 屏幕方向
     * @param rongCallback      回调
     */
    void callInit(Activity gameActivity, int screenOrientation, RongCallback rongCallback);

    /**
     * 登录接口
     */
    void callLogin();

    /**
     * 支付接口
     *
     * @param rongOrder 订单对象
     */
    void callPay(RongOrder rongOrder);

    /**
     * 视频广告接口
     */
    void callStaPay();

    /**
     * 注销接口
     */
    void callLogout();

    /**
     * 提交游戏信息接口
     *
     * @param gameInfo 游戏信息对象
     */
    void callSubGameInfo(RongGameInfo gameInfo);

    /**
     * 生命周期方法onNewIntent
     *
     * @param intent 透传
     */
    void callOnNewIntentInvoked(Intent intent);

    /**
     * 生命周期方法onResume
     */
    void callOnResumeInvoked();

    /**
     * 生命周期方法onStart
     */
    void callOnStartInvoked();

    /**
     * 生命周期方法onPause
     */
    void callOnPauseInvoked();

    /**
     * 生命周期方法onStop
     */
    void callOnStopInvoked();

    /**
     * 生命周期方法onDestory
     */
    void callOnDestoryInvoked();

    /**
     * 退出游戏
     */
    void callExitGame();

}

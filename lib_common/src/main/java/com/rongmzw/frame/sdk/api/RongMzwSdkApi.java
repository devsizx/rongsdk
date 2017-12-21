package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;

import com.rongmzw.frame.sdk.callback.RongMzwExitGameCallback;
import com.rongmzw.frame.sdk.callback.RongMzwInitCallback;
import com.rongmzw.frame.sdk.callback.RongMzwLoignCallback;
import com.rongmzw.frame.sdk.callback.RongMzwPayCallback;
import com.rongmzw.frame.sdk.callback.RongMzwStaPayCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongMzwOrder;

/**
 * Created by Administrator on 2017/12/19.
 */
public interface RongMzwSdkApi {
    /**
     * 初始化接口
     *
     * @param gameActivity      游戏主Activity
     * @param screenOrientation 屏幕方向
     * @param initCallback      初始化回调
     */
    void callInit(Activity gameActivity, int screenOrientation, RongMzwInitCallback initCallback);

    /**
     * 登录接口
     *
     * @param loginCallback 登录回调
     */
    void callLogin(RongMzwLoignCallback loginCallback);

    /**
     * 支付接口
     *
     * @param rongMzwOrder 订单对象
     * @param payCallback  支付回调
     */
    void callPay(RongMzwOrder rongMzwOrder, RongMzwPayCallback payCallback);

    /**
     * 视频广告接口
     *
     * @param staPayCallback 视频广告回调
     */
    void callStaPay(RongMzwStaPayCallback staPayCallback);

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
     *
     * @param exitGameCallback 退出游戏回调
     */
    void callExitGame(RongMzwExitGameCallback exitGameCallback);
}

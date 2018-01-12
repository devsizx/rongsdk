package com.rongmzw.frame.sdk.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.anzhi.sdk.middle.manage.AnzhiSDK;
import com.anzhi.sdk.middle.manage.GameCallBack;
import com.anzhi.sdk.middle.util.MD5;
import com.muzhiwan.sdk.core.callback.MzwInitCallback;
import com.muzhiwan.sdk.core.callback.MzwLoignCallback;
import com.muzhiwan.sdk.core.callback.MzwPayCallback;
import com.muzhiwan.sdk.service.MzwOrder;
import com.rongmzw.frame.sdk.activity.WebActivity;
import com.rongmzw.frame.sdk.callback.RongCallBackUtils;
import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.callback.RongHttpCallback;
import com.rongmzw.frame.sdk.constants.RongConstants;
import com.rongmzw.frame.sdk.domain.http.InitResponse;
import com.rongmzw.frame.sdk.domain.http.LoginResponse;
import com.rongmzw.frame.sdk.domain.http.PayResponse;
import com.rongmzw.frame.sdk.domain.local.RongGameInfo;
import com.rongmzw.frame.sdk.domain.local.RongOrder;
import com.rongmzw.frame.sdk.impl.RongSdkRequest;
import com.rongmzw.frame.sdk.util.Des3Util;
import com.rongmzw.frame.sdk.utils.LocalSpfManagerUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RongSdkController extends RongSdkRequest implements RongSdkApi {
    private static String TAG = RongSdkController.class.getSimpleName();

    private String appkey = "11111";// SDK 初始化参数
    private String appSecret = "1111";
    private AnzhiSDK anzhiSDK = AnzhiSDK.getInstance();
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
                    RongCallBackUtils.initSuccessCallBack(result);
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
                            final String userInfo = "{\"cptoken\":\"" + cptoken + "\",\"deviceId\":\"" + deviceId + "\",\"requestUrl\":\"" + requestUrl + "\"}";
                            loginRequest(gameActivity, userInfo, new RongHttpCallback() {
                                @Override
                                public void onSuccess(String type, String msg) {
//                                    msg = "{\"data\":{\"token\":\"5d41402abc4b2a76b9719d911017c592\",\"mzwid\":\"8832975\",\"bind\":1},\"err_no\":200,\"err_msg\":\"\",\"time\":1515397518}";
                                    loginResponse = gson.fromJson(msg, LoginResponse.class);
                                    if (loginResponse.getErr_no() == RongConstants.SUCCESSCODE && loginResponse.getData() != null) {
                                        String token = loginResponse.getData().getToken();
                                        String url = initResponse.getData().getBindurl();
                                        String mzwid = loginResponse.getData().getMzwid();
                                        LocalSpfManagerUtils.putStringShared(gameActivity, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_MZWID, mzwid);
                                        if (initResponse.getData().getSwitchX() != RongConstants.SWITCH_NORMAL && loginResponse.getData().getBind() == RongConstants.BIND) {
                                            Intent intent = new Intent(gameActivity, WebActivity.class);
                                            intent.putExtra("token", token);
                                            intent.putExtra("url", url);
                                            intent.putExtra("mzwid", mzwid);
                                            gameActivity.startActivity(intent);
                                        } else {
                                            RongCallBackUtils.loginSuccessCallBack(loginResponse.getData().getToken());
                                        }
                                    } else {
                                        RongCallBackUtils.loginFailedCallBack(loginResponse.getErr_msg());
                                    }
                                }

                                @Override
                                public void onFailed(String type, String msg) {
                                    RongCallBackUtils.loginFailedCallBack("binding  failed...");
                                }
                            });
                        } else { // 登录失败
                            String desc = json.optString("code_desc");
                            RongCallBackUtils.loginFailedCallBack(desc);
                        }
                    } catch (JSONException e) {
                    }
                    break;
                case GameCallBack.SDK_TYPE_LOGOUT: // 注销登录操作
                    RongCallBackUtils.logoutCallBack();
                    break;
                case GameCallBack.SDK_TYPE_PAY:
                    RongCallBackUtils.payCallBack(type, result);
                    break;
                case GameCallBack.SDK_TYPE_CANCEL_PAY:
                    RongCallBackUtils.payCallBack(type, result);
                    break;
                case GameCallBack.SDK_TYPE_EXIT_GAME: // 退出游戏操作
                    RongCallBackUtils.exitGameSuccessCallBack("exitgame success");
                    break;
                case GameCallBack.SDK_TYPE_CANCEL_EXIT_GAME: // 取消退出游戏操作
                    RongCallBackUtils.exitGameFailedCallBack("exitgame cancel");
                    break;
                default:
                    break;
            }
        }
    };

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
        this.gameActivity = gameActivity;
        RongCallBackUtils.rongCallback = rongCallback;
        initRequest(gameActivity, new RongHttpCallback() {
            @Override
            public void onSuccess(String type, String msg) {
//                msg = "{\"data\":{\"switch\":2,\"apilevel\":0,\"bindurl\":\"http:\\/\\/sdk.muzhiwan.com\\/pay\\/V1\\/move\\/?id=6268bc958aac428dfc6e93216708354f&key=6b70c8a75142e26ea8192ac9939a57cb\",\"params\":{\"appkey\":\"1378375366Az26xatNyDOD5EM6D2ys\",\"secret\":\"ug2KMdLi2JSr4naOE48XmL3h\"}},\"err_no\":200,\"err_msg\":\"\",\"time\":1515396337}";
                initResponse = gson.fromJson(msg, InitResponse.class);
                if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
                    Log.e(TAG, "mzw callInit......");
                    mzwSdkController.init(gameActivity, screenOrientation, new MzwInitCallback() {
                        @Override
                        public void onResult(int i, String s) {
                            RongCallBackUtils.initCallBack(i, s);
                        }
                    });
                } else {
                    Log.e(TAG, "anzhi callInit......");
                    appkey = initResponse.getData().getParams().getAppkey();
                    appSecret = initResponse.getData().getParams().getSecret();
                    anzhiSDK.init(gameActivity, appkey, appSecret, callback);
                }
            }

            @Override
            public void onFailed(String type, String msg) {
                RongCallBackUtils.initFailedCallBack(msg);
            }
        });
    }

    @Override
    public void callLogin() {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            Log.e(TAG, "mzw callLogin......");
            mzwSdkController.doLogin(new MzwLoignCallback() {
                @Override
                public void onResult(int i, String s) {
                    RongCallBackUtils.loginCallBack(i, s);
                }
            });
        } else {
            Log.e(TAG, "anzhi callLogin......");
            anzhiSDK.login(this.gameActivity);
        }
    }

    @Override
    public void callPay(final RongOrder rongMzwOrder) {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            MzwOrder mzwOrder = new MzwOrder();
            mzwOrder.setMoney(rongMzwOrder.getProductPrice());
            mzwOrder.setProductdesc(rongMzwOrder.getProductDesc());
            mzwOrder.setProductid(rongMzwOrder.getProductId());
            mzwOrder.setProductname(rongMzwOrder.getProductName());
            mzwSdkController.doPay(mzwOrder, new MzwPayCallback() {
                @Override
                public void onResult(int i, MzwOrder mzwOrder) {
                    RongCallBackUtils.payCallBack(i, mzwOrder.toString());
                }
            });
        } else {
            Log.e(TAG, "anzhi callPay......rongMzwOrder--->" + rongMzwOrder);
            final String productId = rongMzwOrder.getProductId();
            final String productName = rongMzwOrder.getProductName();
            final String productDesc = rongMzwOrder.getProductDesc();
            final String extern = rongMzwOrder.getExtern();
            final int productPrice = rongMzwOrder.getProductPrice();
            final String orderInfo = "{\"productid\":\"" + productId + "\",\"subject\":\"" + productName + "\",\"productdesc\":\"" + productDesc + "\",\"amount\":\"" + productPrice + "\",\"extern\":\"" + extern + "\"}";
            payRequest(gameActivity, orderInfo, new RongHttpCallback() {
                @Override
                public void onSuccess(String type, String msg) {
                    payResponse = gson.fromJson(msg.toString(), PayResponse.class);
                    JSONObject json = new JSONObject();
                    try {
                        // 游戏方生成的订单号,可以作为与安智订单进行关联
                        json.put("amount", payResponse.getData().getAmount());// 支付金额(单位：分)
                        json.put("productCode", productId);// 游戏方商品代码
                        json.put("productName", productName);// 游戏方商品名称
                        json.put("cpCustomInfo", productDesc);// 游戏方自定义数据
                        json.put("productCount", 1);// 商品数量
                        json.put("cpOrderId", payResponse.getData().getOrder_no());
                        Log.e("wangzhixin", payResponse.getData().getOrder_no().substring(6));
                        json.put("cpOrderTime", payResponse.getData().getOrder_no().substring(6));// 下单时间
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String data = json.toString();
                    anzhiSDK.pay(Des3Util.encrypt(data, appSecret), MD5.encodeToString(appSecret));
                }

                @Override
                public void onFailed(String type, String msg) {

                }
            });
        }
    }

    @Override
    public void callStaPay() {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            Log.e(TAG, "mzw callStaPay......");
        } else {
            Log.e(TAG, "anzhi callStaPay......");
        }
    }

    @Override
    public void callLogout() {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            Log.e(TAG, "mzw callLogout......");
            mzwSdkController.doLogout();
        } else {
            Log.e(TAG, "anzhi callLogout......");
            anzhiSDK.logout();
        }
    }

    @Override
    public void callSubGameInfo(RongGameInfo gameInfo) {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            Log.e(TAG, "mzw callSubGameInfo......gameInfo---->" + gameInfo.toString());
            mzwSdkController.subGameInfo(gameInfo.toString());
        } else {
            Log.e(TAG, "anzhi callSubGameInfo.....gameInfo--->" + gameInfo);
            anzhiSDK.subGameInfo(com.alibaba.fastjson.JSONObject.toJSONString(gameInfo));
        }
    }

    @Override
    public void callOnNewIntentInvoked(Intent intent) {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnNewIntentInvoked......");
//        } else {
        Log.e(TAG, "anzhi callOnNewIntentInvoked......");
        anzhiSDK.onNewIntentInvoked(intent);
//        }
    }

    @Override
    public void callOnResumeInvoked() {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnResumeInvoked......");
//        } else {
        Log.e(TAG, "anzhi callOnResumeInvoked......");
        anzhiSDK.onResumeInvoked();
//        }
    }

    @Override
    public void callOnStartInvoked() {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnStartInvoked......");
//        } else {
        Log.e(TAG, "anzhi callOnStartInvoked......");
        anzhiSDK.onStartInvoked();
//        }
    }

    @Override
    public void callOnPauseInvoked() {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnPauseInvoked......");
//        } else {
        Log.e(TAG, "anzhi callOnPauseInvoked......");
        anzhiSDK.onPauseInvoked();
//        }
    }

    @Override
    public void callOnStopInvoked() {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnStopInvoked......");
//        } else {
        Log.e(TAG, "anzhi callOnStopInvoked......");
        anzhiSDK.onStopInvoked();
//        }
    }

    @Override
    public void callOnDestoryInvoked() {
//        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH) {
//            Log.e(TAG, "mzw callOnDestoryInvoked......");
//            mzwSdkController.destory();
//        } else {
        Log.e(TAG, "anzhi callOnDestoryInvoked......");
        anzhiSDK.onDestoryInvoked();
//        }
    }

    @Override
    public void callExitGame() {
        if (initResponse.getData().getSwitchX() == RongConstants.SWITCH_DIE) {
            Log.e(TAG, "mzw callExitGame......");
            RongCallBackUtils.exitGameSuccessCallBack("exitGame success......");
        } else {
            Log.e(TAG, "anzhi callExitGame......");
            anzhiSDK.exitGame(this.gameActivity);
        }
    }
}

package com.rongmzw.frame.sdk.utils;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.muzhiwan.sdk.common.utils.StringManagerUtils;
import com.rongmzw.frame.sdk.callback.RongHttpCallback;
import com.rongmzw.frame.sdk.constants.HttpConstants;
import com.rongmzw.frame.sdk.constants.RongConstants;
import com.rongmzw.frame.sdk.domain.http.HttpRequestCache;
import com.rongmzw.frame.sdk.domain.http.InitResponse;
import com.rongmzw.frame.sdk.domain.http.LoginResponse;
import com.rongmzw.frame.sdk.domain.http.PayResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();
    private static Gson gson = new Gson();
    private static HttpRequestCache httpRequestCache;
    private static String secretkey;
    private static int initRequestTimes = 0;
    private static int loginRequestTimes = 0;
    private static int payRequestTimes = 0;
    private static final int TIMEMAX = 5;

    public static void initRequest(final Activity activity, Map<String, String> params, final RongHttpCallback initHttpCallback) {
        OkHttpUtils.get().url(getUrl(getInitParamsString(activity, params, initHttpCallback))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                initHttpCallback.onFailed("init", "" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "initResponse-->" + response.toString());
                InitResponse initResponse = gson.fromJson(response.toString(), InitResponse.class);
                if (initResponse != null) {
                    if (initResponse.getErr_no() == RongConstants.SUCCESSCODE) {
                        initRequestTimes = 0;
                        initHttpCallback.onSuccess("init", response.toString());
                    } else if (initResponse.getErr_no() == RongConstants.SIGNERRORCODE && initRequestTimes < TIMEMAX) {
                        getSecretKey(null, true);
                    } else {
                        initHttpCallback.onFailed("init", response.toString());
                    }
                } else {
                    initHttpCallback.onFailed("init", response.toString());
                }
            }
        });
    }

    public static void loginRequest(final Activity activity, Map<String, String> params, final RongHttpCallback loginHttpCallback) {
        OkHttpUtils.get().url(getUrl(getLoginParamsString(activity, params, loginHttpCallback))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loginHttpCallback.onFailed("login", "" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "loginResponse-->" + response.toString());
                LoginResponse loginResponse = gson.fromJson(response.toString(), LoginResponse.class);
                if (loginResponse != null) {
                    if (loginResponse.getErr_no() == RongConstants.SUCCESSCODE) {
                        loginRequestTimes = 0;
                        loginHttpCallback.onSuccess("login", response.toString());
                    } else if (loginResponse.getErr_no() == RongConstants.SIGNERRORCODE && loginRequestTimes < TIMEMAX) {
                        getSecretKey(null, true);
                    } else {
                        loginHttpCallback.onFailed("login", response.toString());
                    }

                } else {
                    loginHttpCallback.onFailed("login", response.toString());
                }
            }
        });
    }

    public static void payRequest(final Activity activity, Map<String, String> params, final RongHttpCallback payHttpCallback) {
        OkHttpUtils.get().url(getUrl(getPayParamsString(activity, params, payHttpCallback))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                payHttpCallback.onFailed("pay", "" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "payResponse-->" + response.toString());
                PayResponse payResponse = gson.fromJson(response.toString(), PayResponse.class);
                if (payResponse != null) {
                    if (payResponse.getErr_no() == RongConstants.SUCCESSCODE) {
                        payRequestTimes = 0;
                        payHttpCallback.onSuccess("pay", response.toString());
                    } else if (payResponse.getErr_no() == RongConstants.SIGNERRORCODE && payRequestTimes < TIMEMAX) {
                        getSecretKey(null, true);
                    } else {
                        payHttpCallback.onFailed("pay", response.toString());
                    }

                } else {
                    payHttpCallback.onFailed("pay", response.toString());
                }
            }
        });
    }

    public static String getUrl(String paramsStrings) {
        return HttpConstants.BASE_URL + paramsStrings;
    }

    public static String getInitParamsString(Activity activity, Map<String, String> params, RongHttpCallback initHttpCallback) {
        if (params == null) {
            params = ParamUtils.getGeneralParams(activity);
        }
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_INIT);
        params.put(HttpConstants.PARAMS_KEY_PARAMS, "[" + LocalSpfManagerUtils.getMzwIds(activity) + "]");
        if (httpRequestCache == null) {
            httpRequestCache = new HttpRequestCache();
        }

        httpRequestCache.setActivity(activity);
        httpRequestCache.setUrl(HttpConstants.BASE_URL);
        httpRequestCache.setParamsMap(params);
        httpRequestCache.setCallback(initHttpCallback);
        httpRequestCache.setRequestType(HttpRequestCache.REQUEST_TYPE_INIT);
        addKey(params, secretkey);
        return convertParams(params);
    }

    public static String getLoginParamsString(Activity activity, Map<String, String> params, RongHttpCallback loginHttpCallback) {
        if (params == null) {
            params = ParamUtils.getGeneralParams(activity);
        }
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_LOGIN);
        if (httpRequestCache == null) {
            httpRequestCache = new HttpRequestCache();
        }

        httpRequestCache.setActivity(activity);
        httpRequestCache.setUrl(HttpConstants.BASE_URL);
        httpRequestCache.setParamsMap(params);
        httpRequestCache.setCallback(loginHttpCallback);
        httpRequestCache.setRequestType(HttpRequestCache.REQUEST_TYPE_LOGIN);
        addKey(params, secretkey);
        return convertParams(params);
    }

    public static String getPayParamsString(Activity activity, Map<String, String> params, RongHttpCallback payHttpCallback) {
        if (params == null) {
            params = ParamUtils.getGeneralParams(activity);
        }
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_PAY);
        String mzwid = LocalSpfManagerUtils.getStringShared(activity, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_MZWID, "");
        params.put(HttpConstants.PARAMS_KEY_UID, mzwid);
        if (httpRequestCache == null) {
            httpRequestCache = new HttpRequestCache();
        }

        httpRequestCache.setActivity(activity);
        httpRequestCache.setUrl(HttpConstants.BASE_URL);
        httpRequestCache.setParamsMap(params);
        httpRequestCache.setCallback(payHttpCallback);
        httpRequestCache.setRequestType(HttpRequestCache.REQUEST_TYPE_PAY);

        addKey(params, secretkey);
        return convertParams(params);
    }

    public static String convertParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        try {
            if (params != null && params.size() > 0) {
                sb.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = String.valueOf(entry.getValue());
                    value = URLEncoder.encode(value == null ? "null" : value, "UTF-8");
                    sb.append(entry.getKey()).append("=").append(value).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }

    public static String covertParams4Sign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        try {
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getKey().equals(HttpConstants.PARAMS_KEY_KEY)) {
                        continue;
                    }
                    String value = String.valueOf(entry.getValue());
                    value = URLEncoder.encode(value == null ? "null" : value, "UTF-8");
                    sb.append(entry.getKey()).append("=").append(value).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }

    public static void getSecretKey(final RongHttpCallback getSecretKeyCallback, final boolean isResend) {
        OkHttpUtils.get().url(HttpConstants.INIT_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                secretkey = response.toString();
                if (isResend) {
                    reSendHttp();
                }
                if (getSecretKeyCallback != null) {
                    getSecretKeyCallback.onSuccess("getsecret", secretkey);
                }
            }
        });
    }

    public static void reSendHttp() {
        if (httpRequestCache != null) {
            switch (httpRequestCache.getRequestType()) {
                case HttpRequestCache.REQUEST_TYPE_INIT:
                    initRequestTimes++;
                    initRequest(httpRequestCache.getActivity(), httpRequestCache.getParamsMap(), httpRequestCache.getCallback());
                    break;
                case HttpRequestCache.REQUEST_TYPE_LOGIN:
                    loginRequestTimes++;
                    loginRequest(httpRequestCache.getActivity(), httpRequestCache.getParamsMap(), httpRequestCache.getCallback());
                    break;
                case HttpRequestCache.REQUEST_TYPE_PAY:
                    payRequestTimes++;
                    payRequest(httpRequestCache.getActivity(), httpRequestCache.getParamsMap(), httpRequestCache.getCallback());
                    break;
            }
        }
    }

    public static void addKey(Map<String, String> params, String secret) {
        params.put(HttpConstants.PARAMS_KEY_KEY, "");// 框架默认的ConcurrentHashMap,当map长度改变时，会发生序列重排的情况，所以先放入key，导致其序列改变，然后进行替换，避免序列改变的情况
        params.put(HttpConstants.PARAMS_KEY_KEY, initGeneralKey(params, secret));
    }

    public static String initGeneralKey(Map<String, String> params, String secret) {
        String sign = "";
        try {
            secret = getSecret(secret);
            String paramString = covertParams4Sign(params);
            paramString += secret;
            URI uri = new URI(HttpConstants.BASE_URL);
            String path = uri.getHost() + uri.getPath();
            sign = SecurityUtils.getMd5(SecurityUtils.getMd5(path, "UTF-8") + paramString, "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static String getSecret(String msg) {
        if (StringManagerUtils.isNull(msg)) {
            return "";
        }
        String res = "";
        byte[] bt = msg.getBytes();
        for (int i = 0; i < bt.length; i = i + 2) {
            if (bt[i] == '0') {
                bt[i] = '9';
                continue;
            }
            if (bt[i] == 'a') {
                bt[i] = 'f';
                continue;
            }
            bt[i] = (byte) (bt[i] - 1);
        }
        try {
            res = new String(bt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return res;
    }
}

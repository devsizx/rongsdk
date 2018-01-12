package com.rongmzw.frame.sdk.utils;

import android.app.Activity;
import android.util.Log;

import com.rongmzw.frame.sdk.callback.RongHttpCallback;
import com.rongmzw.frame.sdk.constants.HttpConstants;
import com.rongmzw.frame.sdk.constants.RongConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;

import static com.rongmzw.frame.sdk.constants.HttpConstants.PARAMS_KEY_UID;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();

    public static void initRequest(final Activity activity, final RongHttpCallback initHttpCallback) {
        OkHttpUtils.get().url(getUrl(getInitUrl(activity))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                initHttpCallback.onFailed("", "");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response.toString());
                initHttpCallback.onSuccess("init", response.toString());
            }
        });
    }

    public static void loginRequest(final Activity activity, String userinfo, final RongHttpCallback loginHttpCallback) {
        OkHttpUtils.get().url(getUrl(getLoginUrl(activity, userinfo))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loginHttpCallback.onFailed("", "");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response.toString());
                loginHttpCallback.onSuccess("init", response.toString());
            }
        });
    }

    public static void payRequest(final Activity activity, String orderInfo, final RongHttpCallback payHttpCallback) {
        OkHttpUtils.get().url(getUrl(getPayUrl(activity, orderInfo))).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                payHttpCallback.onFailed("", "");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response.toString());
                payHttpCallback.onSuccess("", response.toString());
            }
        });
    }

    public static String getUrl(String paramsStrings) {
        Log.e(TAG, HttpConstants.BASE_URL + paramsStrings);
        return HttpConstants.BASE_URL + paramsStrings;
    }

    public static String getInitUrl(Activity activity) {
        Map<String, String> params = ParamUtils.getGeneralParams(activity);
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_INIT);
        return convertParams(params);
    }

    public static String getLoginUrl(Activity activity, String userInfo) {
        Map<String, String> params = ParamUtils.getGeneralParams(activity);
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_LOGIN);
        params.put(HttpConstants.PARAMS_KEY_USERINFO, userInfo);
        return convertParams(params);
    }

    public static String getPayUrl(Activity activity, String orderInfo) {
        Map<String, String> params = ParamUtils.getGeneralParams(activity);
        params.put(HttpConstants.PARAMS_KEY_TYPE, HttpConstants.PARAMS_VALUE_PAY);
        params.put(HttpConstants.PARAMS_KEY_ORDERINFO, orderInfo);
        String mzwid = LocalSpfManagerUtils.getStringShared(activity, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_MZWID, "");
        params.put(HttpConstants.PARAMS_KEY_UID, mzwid);
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
}

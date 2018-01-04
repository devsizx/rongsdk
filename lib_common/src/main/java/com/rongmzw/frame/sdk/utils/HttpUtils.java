package com.rongmzw.frame.sdk.utils;

import android.app.Activity;
import android.util.Log;

import com.rongmzw.frame.sdk.callback.HttpCallback;
import com.rongmzw.frame.sdk.constants.HttpConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();

    public static void initRequest(final Activity activity, final HttpCallback initHttpCallback) {
        OkHttpUtils.get().url(getUrl(activity, HttpConstants.PARAMS_VALUE_INIT)).build().execute(new StringCallback() {
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

    public static String getUrl(Activity activity, String typeValue) {
        Map<String, String> params = ParamUtils.getGeneralParams(activity);
        params.put(HttpConstants.PARAMS_KEY_TYPE, typeValue);
        Log.e(TAG, HttpConstants.BASE_URL + convertParams(params));
        return HttpConstants.BASE_URL + convertParams(params);
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

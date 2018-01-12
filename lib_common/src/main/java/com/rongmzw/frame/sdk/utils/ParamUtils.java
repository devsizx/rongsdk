package com.rongmzw.frame.sdk.utils;

import android.content.Context;
import android.os.Build;

import com.muzhiwan.sdk.common.utils.MzwSDKManagerUtils;
import com.rongmzw.frame.sdk.constants.HttpConstants;

import java.util.HashMap;
import java.util.Locale;

public class ParamUtils {
    public static HashMap<String, String> getGeneralParams(Context context) {
        String osNetwork = OSInfoManagerUtils.getCurrentNetType(context);
        String network = ("".equals(osNetwork) || "null".equals(osNetwork)) ? "other" : osNetwork;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HttpConstants.PARAMS_KEY_MODEL, Build.MODEL);
        params.put(HttpConstants.PARAMS_KEY_BRAND, Build.MANUFACTURER);
        params.put(HttpConstants.PARAMS_KEY_FINGERPRINT, Build.FINGERPRINT);
        params.put(HttpConstants.PARAMS_KEY_UNIQUEID, OSInfoManagerUtils.getUniqueID(context));
        params.put(HttpConstants.PARAMS_KEY_PACKAGE, context.getPackageName());
        params.put(HttpConstants.PARAMS_KEY_IMEI, OSInfoManagerUtils.getIMEI(context));
        params.put(HttpConstants.PARAMS_KEY_MAC, OSInfoManagerUtils.getMacAddress(context));
        params.put(HttpConstants.PARAMS_KEY_SYSTEMVERSION, String.valueOf(Build.VERSION.SDK_INT));
        params.put(HttpConstants.PARAMS_KEY_CPU, OSInfoManagerUtils.getCpuType(context));
        params.put(HttpConstants.PARAMS_KEY_COUNTRY, Locale.getDefault().getCountry());
        params.put(HttpConstants.PARAMS_KEY_DENSITY, String.valueOf(OSInfoManagerUtils.getScreenDpi(context)));
        params.put(HttpConstants.PARAMS_KEY_WIDTH, String.valueOf(OSInfoManagerUtils.getScreenWidth(context)));
        params.put(HttpConstants.PARAMS_KEY_HEIGHT, String.valueOf(OSInfoManagerUtils.getScreenHeight(context)));
        params.put(HttpConstants.PARAMS_KEY_NETWORK, network);
        params.put(HttpConstants.PARAMS_KEY_APPKEY, MzwSDKManagerUtils.getAppKey(context));
        params.put(HttpConstants.PARAMS_KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        return params;
    }

}

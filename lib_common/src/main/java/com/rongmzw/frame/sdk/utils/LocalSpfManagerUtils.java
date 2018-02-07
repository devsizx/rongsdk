package com.rongmzw.frame.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.muzhiwan.sdk.common.utils.StringManagerUtils;
import com.rongmzw.frame.sdk.constants.RongConstants;

public class LocalSpfManagerUtils {

    public static void putStringShared(Context context, String shareName, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringShared(Context context, String shareName, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void putIntShared(Context context, String shareName, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntShared(Context context, String shareName, String key, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static void putBooleanShared(Context context, String shareName, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getMzwIds(Activity activity) {
        String mzwIds = LocalSpfManagerUtils.getStringShared(activity, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_MZWIDS, "");
        String[] mzwIdArray = mzwIds.split(",");
        String resultIds = "";
        for (int i = 0; i < Math.min(mzwIdArray.length, 3); i++) {
            resultIds = mzwIdArray[i] + "," + resultIds;
        }
        if (resultIds.length() > 0) {
            resultIds = resultIds.substring(0, resultIds.length() - 1);
        }
        return resultIds;
    }

    public static void addMzwId(Activity activity, String mzwid) {
        String mzwIds = getMzwIds(activity);
        if (StringManagerUtils.isNull(mzwIds)) {
            mzwIds = "\"" + mzwid + "\"" + mzwIds;
        } else if (!mzwIds.contains(mzwid)) {
            mzwIds = "\"" + mzwid + "\"," + mzwIds;
        }
        LocalSpfManagerUtils.putStringShared(activity, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_MZWIDS, mzwIds);
    }
}

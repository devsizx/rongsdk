package com.rongmzw.frame.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.rongmzw.frame.sdk.constants.RongConstants;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class OSInfoManagerUtils {
    private static String TAG = OSInfoManagerUtils.class.getSimpleName();

    /**
     * 获取当前手机的网络类型
     *
     * @param context 上下文对象
     * @return 成功返回对应的网络类型(如：wifi、2g、3g、4g)，失败返回null
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                type = "4g";
            }
        }
        return type;
    }


    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context 上下文对象
     * @return 屏幕高度
     */
    public static int getScreenDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取设备IMEI号
     *
     * @param context 上下文对象
     * @return 设备IMEI号
     */
    public static String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取手机MAC地址(只有手机开启wifi才能获取到MAC地址)
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String macSavePath = FileManagerUtils.getMacAddressSavePath(context);
        String macContent = FileManagerUtils.readFile(macSavePath);
        if (TextUtils.isEmpty(macContent)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            macContent = wifiInfo.getMacAddress();
            FileManagerUtils.writeFile(macSavePath, macContent);
        }
        return macContent;
    }

    /**
     * 获取uniqueID
     *
     * @param context
     * @return
     */
    public static String getUniqueID(Context context) {
        String uniqueIDsavePath = FileManagerUtils.getUniqueIDSavePath(context);
        String uniqueIDcontent = FileManagerUtils.readFile(uniqueIDsavePath);
        if (TextUtils.isEmpty(uniqueIDcontent)) {
            uniqueIDcontent = UUID.randomUUID().toString();
            FileManagerUtils.writeFile(uniqueIDsavePath, uniqueIDcontent);
        }
        return uniqueIDcontent;
    }

    /**
     * 获取cpu类型
     *
     * @return
     */
    public static String getCpuType(Context context) {
        String cpu_key = "cpu_key";
        String cpuType = LocalSpfManagerUtils.getStringShared(context, RongConstants.RONG_SPF_NAME, cpu_key, "");

        if (!TextUtils.isEmpty(cpuType)) {
            return cpuType;
        }

        BufferedReader bufferedReader = null;
        InputStream is = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.board.platform");
            is = p.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String result = bufferedReader.readLine();
            if (!TextUtils.isEmpty(result)) {
                cpuType = result;
            } else {
                cpuType = getMtkType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCloseable(bufferedReader);
            closeCloseable(is);
        }
        if (!TextUtils.isEmpty(cpuType)) {
            LocalSpfManagerUtils.putStringShared(context, RongConstants.RONG_SPF_NAME, cpu_key, cpuType);
        }
        return cpuType;
    }

    private static String getMtkType() {
        BufferedReader bufferedReader = null;
        InputStream is = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.mediatek.platform");
            is = p.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String result = bufferedReader.readLine();
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    public static void closeCloseable(Closeable obj) {
        try {
            if (obj != null && obj instanceof Closeable)
                obj.close();

        } catch (IOException e) {

        }
    }
}

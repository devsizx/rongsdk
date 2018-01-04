package com.rongmzw.frame.sdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class TaskManagerUtils {
    // 检查权限
    public static boolean hasPermission(Context context, String targetPermission) {
        try {
            String[] array = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (array != null) {
                for (String string : array) {
                    if (string.equals(targetPermission)) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
}

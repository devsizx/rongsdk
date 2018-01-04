package com.rongmzw.frame.sdk.utils;

import android.Manifest.permission;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagerUtils {
    // 获取根目录
    public static String getRootDirPath(Context context) {
        if (existSDCard(context)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else {
            return Environment.getRootDirectory() + "/mzwsdk/";
        }
    }

    // 删除文件夹及子文件
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
            } else {
                file.delete();
            }
        }
        return true;
    }

    // 判断SD卡是否存在 判断应用是否加入了sd读写的权限 以及 sd卡是否挂载
    public static boolean existSDCard(Context context) {
        return TaskManagerUtils.hasPermission(context, permission.WRITE_EXTERNAL_STORAGE) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // 获取MacAddress文件路径
    public static String getMacAddressSavePath(Context context) {
        return getChildPath(context, "data/.systemmac");
    }

    // 获取uniqueID文件路径
    public static String getUniqueIDSavePath(Context context) {
        return getChildPath(context, "data/.systemid");
    }

    // 写文件
    public static boolean writeFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            FileOutputStream fos = getOutputStream(file);
            if (fos != null && !TextUtils.isEmpty(content)) {
                fos.write(content.getBytes("UTF-8"));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // 读文件
    public static String readFile(String filePath) {
        String contentStr = "";
        try {
            FileInputStream ins = null;
            File file = new File(filePath);
            if (file.exists()) {
                ins = new FileInputStream(file);
                byte[] buff = new byte[(int) file.length()];
                ins.read(buff);
                contentStr = new String(buff, "UTF-8");
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            contentStr = "";
        }
        return contentStr;
    }

    // 根据子目录获取文件路径
    private static String getChildPath(Context context, String childPath) {
        return getRootDirPath(context) + childPath;
    }

    // 获取文件输出流
    private static FileOutputStream getOutputStream(File file) {
        FileOutputStream fos = null;
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                fos = new FileOutputStream(file);
            } catch (IOException e) {
                fos = null;
            }
        }
        return fos;
    }
}

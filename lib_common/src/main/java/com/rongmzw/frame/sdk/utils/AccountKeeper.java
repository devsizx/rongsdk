package com.rongmzw.frame.sdk.utils;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.rongmzw.frame.sdk.constants.RongConstants;
import com.rongmzw.frame.sdk.domain.http.User;
import com.rongmzw.frame.sdk.domain.http.UserData;
import com.rongmzw.frame.sdk.domain.http.UserDataStore;
import com.rongmzw.frame.sdk.domain.http.UserStore;

import java.io.File;

@SuppressLint("NewApi")
public class AccountKeeper {
    private static AccountKeeper instance;
    private static User user;
    private UserDataStore userDataStore;
    private UserStore userStore;
    private Context context;

    public AccountKeeper(Context mContext) {
        this.context = mContext;
    }

    public void init() {
        File userDataFile = new File(getRootDirPath(this.context) + RongConstants.RONG_USERDATA_PATH_ROOT);
        File userFile = new File(getRootDirPath(this.context) + RongConstants.RONG_USER_PATH_ROOT);
        if (userDataFile.exists()) {
            userDataFile.mkdirs();
        }

        if (userFile.exists()) {
            userFile.mkdirs();
        }
        if (userDataFile != null) {
            userDataStore = new UserDataStore(userDataFile.getAbsolutePath());
        }
        if (userFile != null) {
            userStore = new UserStore(userFile.getAbsolutePath());
        }
    }

    // 添加
    public void addUserData(UserData userdata) {
        if (userdata != null) {
            String key = userdata.getUserName();
            key = SecurityUtils.getMd5(key);
            if (userDataStore == null) {
                init();
            } else {
                userDataStore.addUserData(userdata, key);
            }
        }
    }

    // 保存user信息
    public void addUser(User user) {
        if (user != null) {
            String key = user.getClass().getSimpleName();
            key = SecurityUtils.getMd5(key);
            if (userStore == null) {
                init();
            } else {
                userStore.addUser(user, key);
            }
        }
    }

    // 获取user信息
    public User loadUser(String key) {
        if (userStore == null) {
            init();
        } else {
            return userStore.loadUser(key);
        }
        return null;
    }

    // 获取user信息
    public User loadDefaultUser() {
        String key = SecurityUtils.getMd5(User.class.getSimpleName());
        if (userStore == null) {
            init();
        } else {
            return userStore.loadUser(key);
        }
        return null;
    }


    public String getRootDirPath(Context context) {
        if (TaskManagerUtils.hasPermission(context, permission.WRITE_EXTERNAL_STORAGE) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/muzhiwan/";
        } else {
            return "/data/data/" + context.getPackageName() + "/muzhiwan/";
        }
    }
}

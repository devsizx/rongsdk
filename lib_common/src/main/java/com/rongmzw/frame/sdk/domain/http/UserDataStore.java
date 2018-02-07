package com.rongmzw.frame.sdk.domain.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.rongmzw.frame.sdk.utils.AesStoreSecurity;
import com.rongmzw.frame.sdk.utils.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UserDataStore {
    private String storePath;
    private Gson gson = null;

    public UserDataStore(String storePath) {
        this.storePath = storePath;
        gson = new Gson();
    }

    // 新增
    public void addUserData(UserData user, String key) {
        String filePath = storePath + "/" + key;
        String userJson = gson.toJson(user);
        byte[] bytes;
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if (fileParent != null && !fileParent.exists()) {
            fileParent.mkdirs();
        }
        try {
            bytes = userJson.getBytes("UTF-8");
            bytes = AesStoreSecurity.encrypt(bytes);
            FileUtils.write(filePath, bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 查询
    public UserData loadUserData(String key) {
        UserData userData = null;
        String filePath = storePath + "/" + key;
        byte[] buffer = FileUtils.read(filePath);
        if (buffer != null && buffer.length > 0) {
            try {
                buffer = AesStoreSecurity.decrypt(buffer);
                userData = gson.fromJson(new String(buffer, "utf-8"), UserData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userData;
    }

    // 删除
    public void deleteUserData(String key) {
        String filePath = storePath + "/" + key;
        FileUtils.delete(filePath);
    }

    // 查询所有
    public List<UserData> loadUserDatas() {
        List<UserData> userList = new ArrayList<UserData>();
        File dir = new File(storePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        UserData userData = null;
        if (files != null) {
            for (File file : files) {
                userData = loadUserData(file.getName());
                if (userData != null) {
                    userList.add(userData);
                }
            }
        }

        return userList;
    }

    // 获取存储文件
    public File getStoreFile(String key) {
        String filePath = storePath + "/" + key;
        if (!TextUtils.isEmpty(filePath)) {
            return new File(filePath);
        }
        return null;
    }
}

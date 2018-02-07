package com.rongmzw.frame.sdk.domain.http;

import com.google.gson.Gson;
import com.rongmzw.frame.sdk.utils.AesStoreSecurity;
import com.rongmzw.frame.sdk.utils.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class UserStore {
    private String storePath;
    private Gson gson = null;

    public UserStore(String storePath) {
        this.storePath = storePath;
        gson = new Gson();
    }

    // 新增
    public void addUser(User user, String key) {
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
    public User loadUser(String key) {
        User user = null;
        String filePath = storePath + "/" + key;
        byte[] buffer = FileUtils.read(filePath);
        if (buffer != null) {
            try {
                buffer = AesStoreSecurity.decrypt(buffer);
                user = gson.fromJson(new String(buffer, "utf-8"), User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // 删除
    public void deleteUser(String key) {
        String filePath = storePath + "/" + key;
        FileUtils.delete(filePath);
    }
}

package com.rongmzw.frame.sdk.domain.http;

import java.io.Serializable;

public class UserData implements Serializable {
    private String uid;
    private String token;
    private String userName;
    private String nickName;
    private String pwd;
    private String openid;
    private int fromId;
    private String iconpath;
    private long time;
    private String phone;
    private String email;

    public UserData() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void initWithUser(User user) {
        this.uid = user.getUserid();
        this.time = user.getLogintime();
        this.iconpath = user.getIconpath();
        this.openid = user.getOpenid();
        this.userName = user.getUsername();
        this.fromId = user.getFromid();
        this.pwd = user.getPwd();
        this.phone = user.getPhonenumber();
        this.email = user.getMail();
    }

    @Override
    public String toString() {
        return "UserData [userName=" + userName + ", pwd=" + pwd + ", openid="
                + openid + ", fromId=" + fromId + ", iconpath=" + iconpath
                + ", time=" + time + ", phone=" + phone + ", email=" + email + "]";
    }

}

package com.rongmzw.frame.sdk.domain.http;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userid;
    private String username;
    private String pwd;
    private long logintime;
    private String iconpath;
    private String phonenumber;
    private String mail;
    private String openid;
    private int fromid;
    private int securitylevel;
    private String gametoken;
    private String accesstoken;
    private boolean visitor;

    private String r_username;
    private String r_phone;
    private String bindidcard;

    public boolean isVisitor() {
        return visitor;
    }

    public void setVisitor(boolean visitor) {
        this.visitor = visitor;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public long getLogintime() {
        return logintime;
    }

    public void setLogintime(long logintime) {
        this.logintime = logintime;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getFromid() {
        return fromid;
    }

    public void setFromid(int fromid) {
        this.fromid = fromid;
    }

    public int getSecuritylevel() {
        return securitylevel;
    }

    public void setSecuritylevel(int securitylevel) {
        this.securitylevel = securitylevel;
    }

    public String getGametoken() {
        return gametoken;
    }

    public void setGametoken(String gametoken) {
        this.gametoken = gametoken;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getR_username() {
        return r_username;
    }

    public void setR_username(String r_username) {
        this.r_username = r_username;
    }

    public String getR_phone() {
        return r_phone;
    }

    public void setR_phone(String r_phone) {
        this.r_phone = r_phone;
    }

    public String getBindidcard() {
        return bindidcard;
    }

    public void setBindidcard(String bindidcard) {
        this.bindidcard = bindidcard;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid + ", username=" + username + ", pwd=" + pwd + ", logintime=" + logintime + ", iconpath=" + iconpath + ", phonenumber=" + phonenumber + ", mail=" + mail + ", openid=" + openid + ", fromid=" + fromid + ", securitylevel=" + securitylevel + ", gametoken=" + gametoken + ", accesstoken=" + accesstoken + "]";
    }

}

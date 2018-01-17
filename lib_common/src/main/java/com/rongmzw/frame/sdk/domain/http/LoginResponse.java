package com.rongmzw.frame.sdk.domain.http;

/**
 * Created by different_loyal on 2018/1/8.
 */

public class LoginResponse {
    /**
     * data : {"token":"5d41402abc4b2a76b9719d911017c592","mzwid":"8832975","bind":0}
     * err_no : 200
     * err_msg :
     * time : 1515396341
     */

    private DataBean data;
    private int err_no;
    private String err_msg;
    private int time;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBean {
        /**
         * token : 5d41402abc4b2a76b9719d911017c592
         * mzwid : 8832975
         */

        private String token;
        private String mzwid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMzwid() {
            return mzwid;
        }

        public void setMzwid(String mzwid) {
            this.mzwid = mzwid;
        }
    }
}

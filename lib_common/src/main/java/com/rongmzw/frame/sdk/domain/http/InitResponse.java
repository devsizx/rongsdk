package com.rongmzw.frame.sdk.domain.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by different_loyal on 2018/1/4.
 */

public class InitResponse {
    /**
     * data : {"switch":0,"apilevel":0,"bindurl":"http://sdk.muzhiwan.com/pay/V1/move/?id=6268bc958aac428dfc6e93216708354f&key=6b70c8a75142e26ea8192ac9939a57cb","params":{"appkey":"1378375366Az26xatNyDOD5EM6D2ys","secret":"ug2KMdLi2JSr4naOE48XmL3h"}}
     * err_no : 200
     * err_msg :
     * time : 1515396337
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
         * switch : 0
         * apilevel : 0
         * bindstate : 0
         * bindurl : http://sdk.muzhiwan.com/pay/V1/move/?id=6268bc958aac428dfc6e93216708354f&key=6b70c8a75142e26ea8192ac9939a57cb
         * params : {"appkey":"1378375366Az26xatNyDOD5EM6D2ys","secret":"ug2KMdLi2JSr4naOE48XmL3h"}
         */

        @SerializedName("switch")
        private int switchX;
        private int apilevel;
        private int bindstate;
        private String bindurl;
        private ParamsBean params;

        public int getSwitchX() {
            return switchX;
        }

        public void setSwitchX(int switchX) {
            this.switchX = switchX;
        }

        public int getApilevel() {
            return apilevel;
        }

        public void setApilevel(int apilevel) {
            this.apilevel = apilevel;
        }

        public int getBindstate() {
            return bindstate;
        }

        public void setBindstate(int bindstate) {
            this.bindstate = bindstate;
        }

        public String getBindurl() {
            return bindurl;
        }

        public void setBindurl(String bindurl) {
            this.bindurl = bindurl;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public static class ParamsBean {
            /**
             * appkey : 1378375366Az26xatNyDOD5EM6D2ys
             * secret : ug2KMdLi2JSr4naOE48XmL3h
             */

            private String appkey;
            private String secret;

            public String getAppkey() {
                return appkey;
            }

            public void setAppkey(String appkey) {
                this.appkey = appkey;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }
        }
    }
}

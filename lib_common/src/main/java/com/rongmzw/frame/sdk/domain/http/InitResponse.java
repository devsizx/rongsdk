package com.rongmzw.frame.sdk.domain.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by different_loyal on 2018/1/4.
 */

public class InitResponse {

    /**
     * data : {"switch":0,"apilevel":0,"params":{"appkey":"a49e9411d64ff53eccfdd09ad10a15b3","secret":"3ad7c2ebb96fcba7cda0cf54a2e802f5"}}
     * err_no : 200
     * err_msg :
     * time : 1515030833
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
         * params : {"appkey":"a49e9411d64ff53eccfdd09ad10a15b3","secret":"3ad7c2ebb96fcba7cda0cf54a2e802f5"}
         */

        @SerializedName("switch")
        private int switchX;
        private int apilevel;
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

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public static class ParamsBean {
            /**
             * appkey : a49e9411d64ff53eccfdd09ad10a15b3
             * secret : 3ad7c2ebb96fcba7cda0cf54a2e802f5
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

package com.rongmzw.frame.sdk.domain.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by different_loyal on 2018/1/4.
 */

public class InitData {

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

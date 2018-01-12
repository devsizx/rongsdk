package com.rongmzw.frame.sdk.domain.http;

/**
 * Created by different_loyal on 2018/1/12.
 */

public class PayResponse {

    /**
     * data : {"order_no":"anzhi_1515668760130","amount":100}
     * err_no : 200
     * err_msg :
     * time : 1515668760
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
         * order_no : anzhi_1515668760130
         * amount : 100
         */

        private String order_no;
        private int amount;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}

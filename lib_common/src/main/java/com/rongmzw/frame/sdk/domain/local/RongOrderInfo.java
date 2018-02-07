package com.rongmzw.frame.sdk.domain.local;

/**
 * Created by different_loyal on 2018/2/6.
 */

public class RongOrderInfo {
    private String productid;
    private String subject;
    private String productdesc;
    private String amount;
    private String extern;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExtern() {
        return extern;
    }

    public void setExtern(String extern) {
        this.extern = extern;
    }
}

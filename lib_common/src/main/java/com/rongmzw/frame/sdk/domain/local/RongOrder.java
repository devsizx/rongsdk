package com.rongmzw.frame.sdk.domain.local;

public class RongOrder {
    private String productId;//商品id
    private String productName;//商品名称
    private String productDesc;//商品描述
    private int productPrice;//商品价格
    private String productOrderid;//商品订单id
    private String roleId;//角色id
    private String serverId;//服务器id
    private String userData;//用户信息
    private String extern;//扩展字段

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductOrderid() {
        return productOrderid;
    }

    public void setProductOrderid(String productOrderid) {
        this.productOrderid = productOrderid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getExtern() {
        return extern;
    }

    public void setExtern(String extern) {
        this.extern = extern;
    }

    @Override
    public String toString() {
        return "RongOrder [productid=" + productId +
                ", productname=" + productName +
                ", productdesc=" + productDesc +
                ", productprice=" + productPrice +
                ", productorderid=" + productOrderid +
                ", roleId=" + roleId +
                ", serverId=" + serverId +
                ", userData=" + userData +
                "]";
    }
}

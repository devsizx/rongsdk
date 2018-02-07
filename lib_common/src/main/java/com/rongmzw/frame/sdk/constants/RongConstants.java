package com.rongmzw.frame.sdk.constants;

/**
 * Created by different_loyal on 2018/1/3.
 */

public class RongConstants {
    public static final int MULTIVERSION = 1000;
    public static final int SUCCESSCODE = 200;
    public static final int SIGNERRORCODE = 100030;
    public static final int SWITCH_NORMAL = 0;//子渠道正常
    public static final int SWITCH_DIE = 1;//子渠道跑路或不合作
    public static final int SWITCH_NODIE = 2;//子渠道配合迁移
    public static final int BIND = 1;
    public static final int NOBIND = 0;
    public static final String RONG_SPF_NAME = "mzw_spf";
    public static final String RONG_SPF_KEY_MZWID = "mzwid";
    public static final String RONG_SPF_KEY_BINDURL = "bindurl";
    public static final String RONG_SPF_KEY_MZWIDS = "mzwids";
    public static final String RONG_USER_PATH_ROOT = "user";
    public static final String RONG_USERDATA_PATH_ROOT = "userdata";
    public static final String RONG_SPF_KEY_LOGOUT_STATE = "logout_state";
    public static final String RONG_SPF_KEY_BIND_STATE = "mzwid_bind_state";
}

package com.rongmzw.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rongmzw.frame.sdk.api.RongSdkController;
import com.rongmzw.frame.sdk.callback.RongCallback;
import com.rongmzw.frame.sdk.domain.local.RongGameInfo;
import com.rongmzw.frame.sdk.domain.local.RongOrder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends Activity implements View.OnClickListener {
    private static String TAG = MainActivity.class.getName();
    private EditText pay_edit;
    private Button loginbtn, logoutbtn, paybtn, stapaybtn, subgameinfobtn, exitgamebtn;
    private RongOrder order;
    private boolean isInit = false;
    private boolean isLogin = false;
    private static final int MSG_INIT = 0x01;
    private static final int MSG_LOGIN = 0x02;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    if (msg.arg1 == 0) {
                        isInit = false;
                        showToast("SDK初始化失败");
                    } else {
                        isInit = true;
                        showToast("SDK初始化成功");
                        login();
                    }
                    break;
                case MSG_LOGIN:
                    if (msg.arg1 == 1) {
                        isLogin = true;
                        String token = msg.obj.toString();
                        String tokenUrl = "http://sdk.muzhiwan.com/oauth2/getuser.php?token=" + token;
                        OkHttpUtils.get().url(tokenUrl).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                showToast("获取用户信息失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                showToast("获取用户信息成功:" + response.toString());
                            }
                        });
                    } else if (msg.arg1 == 6) {
                        isLogin = false;
                        login();
                    } else if (msg.arg1 == 4) {
                        isLogin = false;
                        showToast("取消登录");
                    } else {
                        isLogin = false;
                        showToast("登录失败");
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        logoutbtn = (Button) findViewById(R.id.logoutbtn);
        pay_edit = (EditText) findViewById(R.id.payedit);
        paybtn = (Button) findViewById(R.id.paybtn);
        stapaybtn = (Button) findViewById(R.id.stapaybtn);
        subgameinfobtn = (Button) findViewById(R.id.subgameinfobtn);
        exitgamebtn = (Button) findViewById(R.id.exitgamebtn);
        loginbtn.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);
        paybtn.setOnClickListener(this);
        stapaybtn.setOnClickListener(this);
        subgameinfobtn.setOnClickListener(this);
        exitgamebtn.setOnClickListener(this);
        /**
         * 检查网络并进行初始化操作
         */
        checkNetwork();
    }

    public void checkNetwork() {
        // !!!在调用SDK初始化前进行网络检查
        // 当前没有拥有网络
        if (false == isNetworkAvailable(this)) {
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setMessage("网络未连接,请设置网络");
            ab.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent("android.settings.SETTINGS");
                    startActivityForResult(intent, 0);
                }
            });
            ab.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            ab.show();
        } else {
            //融合SDK初始化接口
            RongSdkController.getInstance().callInit(MainActivity.this, RongSdkController.ORIENTATION_HORIZONTAL, new RongCallback() {

                @Override
                public void onResult(int type, int code, String msg) {
                    Message message = new Message();
                    switch (type) {
                        case RongCallback.TYPE_INIT:
                            Log.e(TAG, "initcallback----code:" + code + "--------msg:" + msg);
                            message.what = MSG_INIT;
                            message.arg1 = code;
                            break;
                        case RongCallback.TYPE_LOGIN:
                            Log.e(TAG, "logincallback----code:" + code + "--------msg:" + msg);
                            message.what = MSG_LOGIN;
                            message.arg1 = code;
                            message.obj = msg;
                            break;
                        case RongCallback.TYPE_PAY:
                            Log.e(TAG, "paycallback----code:" + code + "--------order:" + msg);
                            break;
                        case RongCallback.TYPE_STAPAY:
                            Log.e(TAG, "stapaycallback----code:" + code + "--------msg:" + msg);
                            break;
                        case RongCallback.TYPE_EXITGAME:
                            Log.e(TAG, "exitgamecallback----code:" + code + "--------order:" + msg);
                            if (code == 1) {
                                finish();
                            } else {

                            }
                            break;
                        default:
                            break;
                    }
                    mHandler.handleMessage(message);
                }
            });
        }
    }

    public void login() {
        RongSdkController.getInstance().callLogin();
    }

    public void pay(String priceValue) {
        order = new RongOrder();
        order.setProductPrice(Integer.parseInt(priceValue) == 0 ? 1 : Integer.parseInt(priceValue));
        order.setProductName("拇指玩测试商品" + ((int) (Math.random() * 10) + 1));
        order.setProductDesc("成为拇指玩超级会员");
        order.setProductOrderid("sssdf");
        order.setProductOrderid("dfdsf");
        order.setRoleId("roleId");
        order.setServerId("ppsmobile_s1");
        order.setUserData("cp message");
        order.setExtern("xxxxxdsds");
        RongSdkController.getInstance().callPay(order);
    }

    public void stapay() {
        RongSdkController.getInstance().callStaPay();
    }

    public void subGameInfo() {
        RongGameInfo gameInfo = new RongGameInfo();
        gameInfo.setGameArea("gamearea");
        gameInfo.setGameAreaID("ppsmobile_s1");
        gameInfo.setGameLevel("17");
        gameInfo.setRoleId("ppsmobile_s1");
        gameInfo.setUserRole("userrole");
        RongSdkController.getInstance().callSubGameInfo(gameInfo);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        RongSdkController.getInstance().callOnNewIntentInvoked(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        RongSdkController.getInstance().callOnPauseInvoked();
        super.onPause();
    }

    @Override
    protected void onResume() {
        RongSdkController.getInstance().callOnResumeInvoked();
        super.onResume();
    }

    @Override
    protected void onStart() {
        RongSdkController.getInstance().callOnStartInvoked();
        super.onStart();
    }

    @Override
    protected void onStop() {
        RongSdkController.getInstance().callOnStopInvoked();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RongSdkController.getInstance().callOnDestoryInvoked();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongSdkController.getInstance().callOnDestoryInvoked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbtn:
                login();
                break;
            case R.id.logoutbtn:
                logout();
                break;
            case R.id.paybtn:
                clickPayBtn();
                break;
            case R.id.stapaybtn:
                clickStaPayBtn();
                break;
            case R.id.subgameinfobtn:
                if (isInit) {
                    if (isLogin) {
                        subGameInfo();
                    } else {
                        showToast("请先进行登录，再进行操作");
                    }
                } else {
                    showToast("请先初始化SDK，再进行操作");
                }
                break;
            case R.id.exitgamebtn:
                if (isInit) {
                    if (isLogin) {
                        exitgame();
                    } else {
                        showToast("请先进行登录，再进行操作");
                    }
                } else {
                    showToast("请先初始化SDK，再进行操作");
                }
                break;
            default:
                break;
        }
    }

    private void exitgame() {
        RongSdkController.getInstance().callExitGame();
    }

    private void clickStaPayBtn() {
        if (isInit) {
            if (isLogin) {
                stapay();
            } else {
                showToast("请先进行登录，再进行操作");
            }
        } else {
            showToast("请先初始化SDK，再进行操作");
        }
    }

    private void clickPayBtn() {
        if (isInit) {
            if (isLogin) {
                String pay_edit_value = pay_edit.getText().toString().trim();
                if (pay_edit_value.equals("") || pay_edit_value.equals("0")) {
                    showToast("输入数据不合法，转换为默认值1元进行支付");
                    pay_edit_value = "1";
                }
                pay(pay_edit_value);
            } else {
                showToast("请先进行登录，再进行操作");
            }
        } else {
            showToast("请先初始化SDK，再进行操作");
        }
    }

    private void logout() {
        isLogin = false;
        RongSdkController.getInstance().callLogout();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 检测是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getState() == NetworkInfo.State.CONNECTED)
            return true;
        return false;
    }
}

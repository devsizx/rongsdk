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

import com.rongmzw.frame.sdk.api.RongMzwSdkController;
import com.rongmzw.frame.sdk.callback.RongMzwExitGameCallback;
import com.rongmzw.frame.sdk.callback.RongMzwInitCallback;
import com.rongmzw.frame.sdk.callback.RongMzwLoignCallback;
import com.rongmzw.frame.sdk.callback.RongMzwPayCallback;
import com.rongmzw.frame.sdk.callback.RongMzwStaPayCallback;
import com.rongmzw.frame.sdk.domain.RongGameInfo;
import com.rongmzw.frame.sdk.domain.RongMzwOrder;

public class MainActivity extends Activity implements View.OnClickListener {
    private static String TAG = MainActivity.class.getName();
    private EditText pay_edit;
    private Button loginbtn, logoutbtn, paybtn, stapaybtn, subgameinfobtn, exitgamebtn;
    private RongMzwOrder order;
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
                        showToast("登录成功");
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
            RongMzwSdkController.getInstance().callInit(MainActivity.this, RongMzwSdkController.ORIENTATION_HORIZONTAL, new RongMzwInitCallback() {

                @Override
                public void onResult(int code, String msg) {
                    isInit = true;
                    Message message = new Message();
                    message.what = MSG_INIT;
                    message.arg1 = code;
                    mHandler.handleMessage(message);
                }
            });
        }
    }

    public void login() {
        RongMzwSdkController.getInstance().callLogin(new RongMzwLoignCallback() {
            @Override
            public void onResult(int code, String msg) {
                Log.e(TAG, "logincallback----code:" + code + "--------msg:" + msg);
                Message message = new Message();
                message.what = MSG_LOGIN;
                message.arg1 = code;
                mHandler.handleMessage(message);
            }
        });
    }

    public void pay(String priceValue) {
        order = new RongMzwOrder();
        order.setProductPrice(Integer.parseInt(priceValue) == 0 ? 1 : Integer.parseInt(priceValue));
        order.setProductName("拇指玩测试商品" + ((int) (Math.random() * 10) + 1));
        order.setProductDesc("成为拇指玩超级会员");
        order.setProductOrderid("sssdf");
        order.setProductOrderid("dfdsf");
        order.setRoleId("roleId");
        order.setServerId("ppsmobile_s1");
        order.setUserData("cp message");
        RongMzwSdkController.getInstance().callPay(order, new RongMzwPayCallback() {
            @Override
            public void onResult(int code, String result) {
                Log.e(TAG, "paycallback----code:" + code + "--------order:" + result);
            }
        });
    }

    public void stapay() {
        RongMzwSdkController.getInstance().callStaPay(new RongMzwStaPayCallback() {
            @Override
            public void onResult(int code, String msg) {
                Log.e(TAG, "stapaycallback----code:" + code + "--------msg:" + msg);
            }
        });
    }

    public void subGameInfo() {
        RongGameInfo gameInfo = new RongGameInfo();
        gameInfo.setGameArea("gamearea");
        gameInfo.setGameAreaID("gameareaid");
        gameInfo.setGameLevel("17");
        gameInfo.setRoleId("roleid");
        gameInfo.setUserRole("userrole");
        RongMzwSdkController.getInstance().callSubGameInfo(gameInfo);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        RongMzwSdkController.getInstance().callOnNewIntentInvoked(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        RongMzwSdkController.getInstance().callOnPauseInvoked();
        super.onPause();
    }

    @Override
    protected void onResume() {
        RongMzwSdkController.getInstance().callOnResumeInvoked();
        super.onResume();
    }

    @Override
    protected void onStart() {
        RongMzwSdkController.getInstance().callOnStartInvoked();
        super.onStart();
    }

    @Override
    protected void onStop() {
        RongMzwSdkController.getInstance().callOnStopInvoked();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        RongMzwSdkController.getInstance().callOnDestoryInvoked();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongMzwSdkController.getInstance().callOnDestoryInvoked();
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
        RongMzwSdkController.getInstance().callExitGame(new RongMzwExitGameCallback() {
            @Override
            public void onResult(int code, String result) {
                Log.e(TAG, "exitgamecallback----code:" + code + "--------order:" + result);
            }
        });
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
        RongMzwSdkController.getInstance().callLogout();
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

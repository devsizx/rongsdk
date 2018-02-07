package com.rongmzw.frame.sdk.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.muzhiwan.sdk.common.utils.StringManagerUtils;
import com.rongmzw.frame.sdk.callback.RongCallBackUtils;
import com.rongmzw.frame.sdk.callback.RongHttpCallback;
import com.rongmzw.frame.sdk.constants.HttpConstants;
import com.rongmzw.frame.sdk.constants.RongConstants;
import com.rongmzw.frame.sdk.domain.http.User;
import com.rongmzw.frame.sdk.domain.http.UserData;
import com.rongmzw.frame.sdk.utils.AccountKeeper;
import com.rongmzw.frame.sdk.utils.HttpUtils;
import com.rongmzw.frame.sdk.utils.LocalSpfManagerUtils;
import com.rongmzw.frame.sdk.utils.ParamUtils;

import java.util.Map;

public class WebActivity extends Activity {
    private String url = "";
    private String token = "";
    private String mzwId = "";
    private WebView webViewForBind = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        token = getIntent().getStringExtra("token");
        mzwId = getIntent().getStringExtra("mzwid");
        webViewForBind = new WebView(this);
        setContentView(webViewForBind);

        HttpUtils.getSecretKey(new RongHttpCallback() {
            @Override
            public void onSuccess(String type, String msg) {
                String secret = HttpUtils.getSecret(msg);
                if (!StringManagerUtils.isNull(mzwId)) {
                    Map<String, String> params = ParamUtils.getGeneralParams(WebActivity.this);
                    params.put(HttpConstants.PARAMS_KEY_MZWID, mzwId);
                    params.put(HttpConstants.PARAMS_KEY_UID, mzwId);
                    HttpUtils.addKey(params, secret);
                    url += HttpUtils.convertParams(params);
                }
                webViewForBind.loadUrl(url);
            }

            @Override
            public void onFailed(String type, String msg) {
                webViewForBind.loadUrl(url);
            }
        }, false);

        webViewForBind.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webViewForBind.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webViewForBind.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hit = view.getHitTestResult();
                if (hit != null) {
                    return false;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("success.php?data=")) {
                    String userInfoJson = url.substring(url.indexOf("data=") + 5);
                    userInfoJson = new String(Base64.decode(userInfoJson, Base64.NO_WRAP));
                    Gson gson = new Gson();
                    User user = gson.fromJson(userInfoJson, User.class);
                    user.setLogintime(System.currentTimeMillis());
                    AccountKeeper accountKeeper = new AccountKeeper(WebActivity.this);
                    accountKeeper.addUser(user);
                    UserData userData = new UserData();
                    userData.initWithUser(user);
                    accountKeeper.addUserData(userData);
                    LocalSpfManagerUtils.putBooleanShared(WebActivity.this, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_LOGOUT_STATE, false);
                    LocalSpfManagerUtils.putIntShared(WebActivity.this, RongConstants.RONG_SPF_NAME, RongConstants.RONG_SPF_KEY_BIND_STATE, 1);
                    WebActivity.this.finish();
                    RongCallBackUtils.loginSuccessCallBack(token);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}

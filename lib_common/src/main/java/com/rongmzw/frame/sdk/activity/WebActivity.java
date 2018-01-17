package com.rongmzw.frame.sdk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.muzhiwan.sdk.common.utils.StringManagerUtils;
import com.rongmzw.frame.sdk.callback.RongCallBackUtils;

public class WebActivity extends Activity {
    private String url = "";
    private String token = "";
    private String mzwId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        token = getIntent().getStringExtra("token");
        mzwId = getIntent().getStringExtra("mzwid");
        WebView webView = new WebView(this);
        setContentView(webView);

        if (!StringManagerUtils.isNull(mzwId)) {
            url += "?mzwid=" + mzwId;
        }
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongCallBackUtils.loginSuccessCallBack(token);
    }
}

package com.rongmzw.frame.sdk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("wzx", "webActivity  onCreate  method...");
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("http://sdk.muzhiwan.com/pay/V1/move/?id=7491782056e2a8c0a8150dda9f885bf9&key=d612b493dcd48e7bcf7d893456f2e5c9");
    }
}

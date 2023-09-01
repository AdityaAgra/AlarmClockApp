package com.example.alarmclockdb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        MyJavascriptInterface interfaceInstance = new MyJavascriptInterface(this,myWebView);
        myWebView.addJavascriptInterface(interfaceInstance, "Android");
        myWebView.loadUrl("http://10.72.216.48:3000/");
        interfaceInstance.getAllAlarm();
    }
}
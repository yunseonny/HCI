package com.example.temiexpo

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val navigator by lazy { TemiNavigator() }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        // WebView 설정
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true          // JS 사용 허용
        settings.domStorageEnabled = true          // React 앱 DOM 저장 허용

        webView.webChromeClient = WebChromeClient()

        // 👇 이 줄이 "웹 ←→ 안드로이드" 연결 핵심
        webView.addJavascriptInterface(TemiJsInterface(navigator), "AndroidTemi")

        // 👇 여기 주소만 너희 웹팀 주소로 바꾸면 됨
        // Vite dev 서버라면 예: "http://192.168.0.23:5173"
        webView.loadUrl("http://192.168.0.52:5173")
//        webView.loadUrl("http://10.0.2.2:5173")
    }

    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

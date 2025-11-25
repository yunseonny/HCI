package com.example.temiexpo

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceError
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.util.Log
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
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadsImagesAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        // ✅ 페이지 로딩/SSL 에러 처리
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                // ⚠️ 인증서 에러 무시하고 강제로 진행
                handler?.proceed()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WEBVIEW", "onPageFinished: $url")
            }
        }

        // ✅ getUserMedia() 권한 요청(카메라 등) 허용
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread {
                    // 디버깅용 로그
                    android.util.Log.d("WEBVIEW_PERM", "onPermissionRequest: ${request.resources.joinToString()}")
                    // JS에서 요청한 리소스(camera, mic 등)를 그대로 허용
                    request.grant(request.resources)
                }
            }
        }

        // 👇 이 줄이 "웹 ←→ 안드로이드" 연결 핵심 (Temi 이동)
        webView.addJavascriptInterface(TemiJsInterface(navigator), "AndroidTemi")

        // 👇 Temi 실제 음성 출력용 JS 브릿지 (window.Robot.speak(...))
        webView.addJavascriptInterface(TemiTtsInterface(), "Robot")

        // 👇 여기 주소만 너희 웹팀 주소로 바꾸면 됨 (현재는 Vite dev 서버 예시)
//        webView.loadUrl("http://192.168.0.3:5173")
        webView.loadUrl("https://temi-project.vercel.app")
        // webView.loadUrl("http://10.0.2.2:5173")  // 에뮬레이터용 예시
    }

    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
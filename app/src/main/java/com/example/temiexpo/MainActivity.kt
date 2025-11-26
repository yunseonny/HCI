package com.example.temiexpo

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    // TemiNavigator 생성 (도착 시 JS 함수 호출)
    private val navigator: TemiNavigator by lazy {
        TemiNavigator(
            onArrived = { waypoint ->
                runOnUiThread {
                    if (::webView.isInitialized) {
                        val safe = JSONObject.quote(waypoint)
                        val js = "window.onTemiArrived && window.onTemiArrived($safe);"
                        webView.evaluateJavascript(js, null)
                    }
                }
            }
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        // ⭐ [필수] 앱 시작 시 기존 웹 캐시 삭제 (수정된 JS 반영을 위해)
        webView.clearCache(true)

        // Temi 리스너 등록
        navigator.init()

        // WebView 설정
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadsImagesAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        // ⭐ [필수] 캐시 모드: 네트워크에서 무조건 로드
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        // WebViewClient (SSL 에러 무시 & 로딩 로그)
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed() // 전시용이라 SSL 에러 무시
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WEBVIEW", "Page Loaded: $url")
            }
        }

        // WebChromeClient (카메라/마이크 권한 허용)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread {
                    request.grant(request.resources)
                }
            }
        }

        // JS 인터페이스 등록
        webView.addJavascriptInterface(TemiJsInterface(navigator), "AndroidTemi") // 이동용
        webView.addJavascriptInterface(TemiTtsInterface(), "Robot")               // 음성용

        // 웹 페이지 로드
        webView.loadUrl("https://temi-project.vercel.app")
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        navigator.release()
        super.onDestroy()
    }
}
package com.example.temiexpo

import android.util.Log  // 👈 로그 사용을 위해 추가
import android.webkit.JavascriptInterface
import org.json.JSONObject

class TemiJsInterface(
    private val navigator: TemiNavigator
) {

    /**
     * 웹에서 호출: window.AndroidTemi.startNavigation(...)
     */
    @JavascriptInterface
    fun startNavigation(payloadJson: String) {
        // 🔍 1. 신호 수신 로그 (이게 떠야 연결 성공)
        Log.d("TemiJsInterface", "startNavigation 호출됨: $payloadJson")

        try {
            val obj = JSONObject(payloadJson)
            val target = obj.optString("to", "")

            if (target.isNotEmpty()) {
                // 🔍 2. 이동 명령 로그
                Log.d("TemiJsInterface", "이동 명령 실행 -> 목적지: $target")
                navigator.goTo(target)
            } else {
                Log.w("TemiJsInterface", "목적지(to)가 비어있습니다!")
            }

        } catch (e: Exception) {
            // 🔍 3. 에러 로그
            Log.e("TemiJsInterface", "JSON 파싱 오류", e)
        }
    }
}
package com.example.temiexpo

import android.webkit.JavascriptInterface
import org.json.JSONObject

class TemiJsInterface(
    private val navigator: TemiNavigator
) {

    /**
     * 웹에서 호출:
     * window.AndroidTemi.startNavigation(JSON.stringify({
     *   from: "wp_lobby",
     *   to: "wp_ai",
     *   label: "인공지능",
     *   mode: "walk"
     * }))
     */
    @JavascriptInterface
    fun startNavigation(payloadJson: String) {
        try {
            val obj = JSONObject(payloadJson)

            // 웹에서 보내는 목적지 웨이포인트 (Guide.jsx의 waypoint 값)
            val target = obj.optString("to", "")

            if (target.isNotEmpty()) {
                navigator.goTo(target)
            }
        } catch (e: Exception) {
            // 필요하면 Log.e 로 찍어도 됨
            // Log.e("TemiJsInterface", "startNavigation parse error", e)
        }
    }
}
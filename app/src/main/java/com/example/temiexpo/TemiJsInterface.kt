package com.example.temiexpo

import android.webkit.JavascriptInterface
import org.json.JSONObject

class TemiJsInterface(
    private val navigator: TemiNavigator
) {

    // 웹팀이 부르는 이름: window.AndroidTemi.startNavigation(JSON.stringify({...}))
    @JavascriptInterface
    fun startNavigation(payloadJson: String) {
        try {
            val obj = JSONObject(payloadJson)
            // 웹팀 코드에서 { from: ..., to: ..., mode: ... } 이렇게 보내니까
            val target = obj.optString("to", "")
            if (target.isNotEmpty()) {
                navigator.goTo(target)
            }
        } catch (e: Exception) {
            // 필요하면 로그 찍기
        }
    }
}

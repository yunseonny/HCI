// TemiTtsInterface.kt
package com.example.temiexpo

import android.util.Log
import android.webkit.JavascriptInterface
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import org.json.JSONObject   // ✅ 추가

class TemiTtsInterface(
    private val robot: Robot = Robot.getInstance()
) {
    @JavascriptInterface
    fun speak(text: String?) {
        if (text.isNullOrEmpty()) return

        Log.d("TemiTTS", "TTS 요청 텍스트: $text")

        try {
            val req = TtsRequest.create(
                text,
                true,                            // 화면 표시 ON
                TtsRequest.Language.KO_KR,
                false
            )
            robot.speak(req)
        } catch (e: Exception) {
            Log.e("TemiTTS", "TTS 오류, 기본 설정으로 재시도", e)
            robot.speak(TtsRequest.create(text, true))
        }
    }

    // ✅ JSON 기반 TTS: { "text": "...", "showOnRobot": false } 형태
    @JavascriptInterface
    fun speakJson(json: String?) {
        if (json.isNullOrEmpty()) return

        try {
            val obj = JSONObject(json)
            val text = obj.optString("text", "")
            if (text.isEmpty()) return

            val showOnRobot = obj.optBoolean("showOnRobot", true)

            Log.d(
                "TemiTTS",
                "TTS JSON 요청: text=$text, showOnRobot=$showOnRobot"
            )

            val req = TtsRequest.create(
                text,
                showOnRobot,                    // ✅ 여기로 넘어감!
                TtsRequest.Language.KO_KR,
                false
            )
            robot.speak(req)
        } catch (e: Exception) {
            Log.e("TemiTTS", "speakJson 오류, fallback 사용", e)
        }
    }
}

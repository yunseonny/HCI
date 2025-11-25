// TemiTtsInterface.kt
package com.example.temiexpo

import android.webkit.JavascriptInterface
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest

class TemiTtsInterface(
    private val robot: Robot = Robot.getInstance()
) {

    /**
     * JS에서: window.Robot.speak("안녕하세요, 테미입니다");
     */
    @JavascriptInterface
    fun speak(text: String) {
        if (text.isBlank()) return


        // 필요하면 queueMode, language 등 옵션도 여기서 설정 가능
        val req = TtsRequest.create(text, /* showOnScreen = */ true)
        robot.speak(req)
    }
}
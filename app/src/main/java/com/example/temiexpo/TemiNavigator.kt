package com.example.temiexpo

import com.robotemi.sdk.Robot

class TemiNavigator(
    private val robot: Robot = Robot.getInstance()
) {
    // ▶ 여기다가 테미에서 실제로 저장한 위치 이름을 적어
    // 웹에서 Guide.jsx에서 우리가 매핑해둔 이름들이랑 맞춰야 함
    private val validLocations = setOf(
        "entrance",    // 홈에서 길안내 누를 때
        "hall_1",
        "hall_2a",
        "hall_2b",
        "hall_3a",
        "hall_3b",
        "photo_spot",
        "games_zone",
        "1홀",
        "2A홀",
        "2B홀"
    )

    fun goTo(location: String) {
        // 만약 웹에서 잘못된 이름을 보내면 그냥 무시
        if (!validLocations.contains(location)) {
            return
        }
        robot.goTo(location)
    }
}

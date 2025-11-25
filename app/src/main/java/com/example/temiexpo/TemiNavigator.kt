package com.example.temiexpo

import com.robotemi.sdk.Robot

class TemiNavigator(
    private val robot: Robot = Robot.getInstance()
) {
    /**
     * Guide.jsx 의 AREAS 설정에서 사용하는 waypoint 이름들.
     * Temi 맵 편집기에서도 가능한 한 이 문자열과 똑같이 저장하는 걸 추천.
     *
     * 예: Temi 앱에서 waypoint 이름을 "wp_ai", "wp_robot" 이런 식으로.
     */
    private val validLocations = setOf(
        //"wp_lobby",

        "휴게 공간1",
        "휴게 공간 2",

        "실감 미디어",
        "부산시 홍보관",
        "데이터 보안",
        "미래 자동차",

        "이차전지",
        "바이오헬스",

        "지능형로봇",
        "에너지신산업",

        "에코업",

        "빅데이터",
        "차세대 디스플레이",

        "인공지능",
        "차세대 통신",

        "첨단소재",

        "COSS 스피어",

        "차세대 반도체",
        "그린 바이오",

        "사물 인터넷",
        "반도체 소부장",

        "항공드론",

        "화장실 1",
        "화장실 2",

        "전시장 입구",
        "전시장 출구"
    )

    fun goTo(location: String) {
        // 웹에서 엉뚱한 값이 오면 그냥 무시
        if (!validLocations.contains(location)) {
            return
        }

        // Temi 에 실제로 존재하는 웨이포인트라고 가정하고 이동
        robot.goTo(location)
    }
}
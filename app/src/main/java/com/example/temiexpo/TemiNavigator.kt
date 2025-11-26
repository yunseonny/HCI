package com.example.temiexpo

import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener

/**
 * 웹(Guide.jsx)에서 넘어온 목적지로 Temi를 이동시키고,
 * 목적지에 '도착(complete)'하면 콜백(onArrived)을 호출해 주는 클래스.
 */
class TemiNavigator(
    private val robot: Robot = Robot.getInstance(),
    // MainActivity 쪽에서 JS 호출용 콜백을 넘겨 줌
    private val onArrived: ((String) -> Unit)? = null
) : OnGoToLocationStatusChangedListener {

    /**
     * Temi 맵에 실제로 저장된 웨이포인트 이름들.
     * (Guide.jsx의 AREAS[].waypoint 값과 맞춰야 함)
     */
    private val validLocations = setOf(
        "휴게공간1", "휴게공간2",
        "실감미디어", "부산시 홍보관", "데이터보안", "미래자동차",
        "이차전지", "바이오헬스", "지능형로봇", "에너지신산업",
        "에코업", "빅데이터", "차세대 디스플레이", "인공지능",
        "차세대통신", "첨단소재", "COSS 스피어", "차세대 반도체",
        "그린바이오", "사물인터넷", "반도체 소부장", "항공드론",
        "화장실 1", "전시장 입구", "전시장 출구"
    )

    /** MainActivity.onCreate() 등에서 꼭 한 번 호출해 줘야 함 */
    fun init() {
        robot.addOnGoToLocationStatusChangedListener(this)
    }

    /** Activity 종료 시 호출해서 리스너 정리 */
    fun release() {
        robot.removeOnGoToLocationStatusChangedListener(this)
    }

    /** 웹에서 넘어온 목적지로 이동 */
    fun goTo(location: String) {
        if (!validLocations.contains(location)) {
            // 잘못된 이름이면 무시 (로그를 남기는 것이 디버깅에 좋습니다)
            println("TemiNavigator: Invalid location requested -> $location")
            return
        }
        robot.goTo(location)
    }

    /**
     * Temi SDK에서 goTo 상태가 바뀔 때마다 불리는 콜백.
     * 수정됨: descriptionId 파라미터 추가
     */
    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int, // 이 부분이 누락되어 있었습니다!
        description: String
    ) {
        // status 값 확인용 로그 (필요시 주석 해제)
        // println("TemiNavigator: Status Changed -> $location, $status")

        if (status == "complete") {
            // MainActivity에게 "여기 도착했어"라고 알려줌
            onArrived?.invoke(location)
        }
    }
}
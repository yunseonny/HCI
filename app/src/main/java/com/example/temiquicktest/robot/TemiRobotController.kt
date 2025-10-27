package com.example.temiquicktest.robot

import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import com.robotemi.sdk.listeners.OnRobotReadyListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TemiRobotController : RobotController, OnRobotReadyListener {

    private val robot: Robot = Robot.getInstance()

    private val _status = MutableStateFlow(GoToStatus.IDLE)
    override val status: StateFlow<GoToStatus> = _status

    private val _currentTarget = MutableStateFlow<String?>(null)
    override val currentTarget: StateFlow<String?> = _currentTarget

    init {
        robot.addOnRobotReadyListener(this)

        // ✅ 리스너 인터페이스로 등록 (파라미터 4개)
        robot.addOnGoToLocationStatusChangedListener(object : OnGoToLocationStatusChangedListener {
            override fun onGoToLocationStatusChanged(
                location: String,
                status: String,
                descriptionId: Int,
                description: String
            ) {
                _currentTarget.value = location
                _status.value = when (status.uppercase()) {
                    "START" -> GoToStatus.START
                    "GOING" -> GoToStatus.GOING
                    "CALCULATING" -> GoToStatus.CALCULATING
                    "REACHED" -> GoToStatus.REACHED
                    "ABORT" -> GoToStatus.ABORT
                    else -> GoToStatus.IDLE
                }
            }
        })
    }

    override fun onRobotReady(isReady: Boolean) {
        // 필요 시 초기화 처리
    }

    override fun goTo(name: String) {
        _currentTarget.value = name
        robot.goTo(name)
    }

    override fun goHome() {
        robot.goTo("home") // 로봇에 home 위치 저장되어 있어야 함
    }

    // ✅ 팔로우: 실제 SDK 메서드명으로 교체
    override fun startFollow() {
        robot.beWithMe()           // 사람 따라오기 시작
    }

    override fun stopFollow() {
        robot.stopMovement()       // 이동/팔로우 정지
    }
}

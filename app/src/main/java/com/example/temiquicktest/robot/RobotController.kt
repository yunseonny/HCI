package com.example.temiquicktest.robot

import kotlinx.coroutines.flow.StateFlow

enum class GoToStatus { IDLE, START, GOING, CALCULATING, REACHED, ABORT }

interface RobotController {
    val status: StateFlow<GoToStatus>
    val currentTarget: StateFlow<String?>
    fun goTo(name: String)
    fun goHome()           // 취소 = 홈으로
    fun startFollow()
    fun stopFollow()
}
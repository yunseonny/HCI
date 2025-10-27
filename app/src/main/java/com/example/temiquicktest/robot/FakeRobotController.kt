package com.example.temiquicktest.robot

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRobotController : RobotController {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _status = MutableStateFlow(GoToStatus.IDLE)
    override val status: StateFlow<GoToStatus> = _status

    private val _currentTarget = MutableStateFlow<String?>(null)
    override val currentTarget: StateFlow<String?> = _currentTarget

    override fun goTo(name: String) {
        _currentTarget.value = name
        scope.launch {
            _status.value = GoToStatus.START
            delay(600); _status.value = GoToStatus.CALCULATING
            delay(800); _status.value = GoToStatus.GOING
            delay(1500); _status.value = GoToStatus.REACHED
            delay(400); _status.value = GoToStatus.IDLE
        }
    }
    override fun goHome() { goTo("home") }
    override fun startFollow() {}
    override fun stopFollow() {}
}
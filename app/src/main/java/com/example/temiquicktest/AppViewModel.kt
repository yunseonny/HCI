package com.example.temiquicktest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.temiquicktest.robot.RobotController
import com.example.temiquicktest.robot.GoToStatus
import kotlinx.coroutines.flow.StateFlow

class AppViewModel(private val robot: RobotController) : ViewModel() {
    val status: StateFlow<GoToStatus> = robot.status
    val target: StateFlow<String?> = robot.currentTarget

    fun goTo(name: String) = robot.goTo(name)
    fun cancelGuide() = robot.goHome()
    fun followStart() = robot.startFollow()
    fun followStop() = robot.stopFollow()

    companion object {
        fun factory(rc: RobotController) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(rc) as T
            }
        }
    }
}
package com.example.temiquicktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.temiquicktest.robot.TemiRobotController
import com.example.temiquicktest.robot.FakeRobotController
import com.example.temiquicktest.temiui.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ▶ 실기기 연결이면 TemiRobotController(), 로봇 없이 UI만 보면 FakeRobotController()
        val controller = TemiRobotController()    // ← 필요시 FakeRobotController()로 교체
//        val controller = FakeRobotController()
        val vm = ViewModelProvider(this, AppViewModel.factory(controller))
            .get(AppViewModel::class.java)

        setContent {
            val nav = rememberNavController()
            AppNavGraph(nav = nav, vm = vm)       // 하위 패키지명을 temiui로 쓴 버전
        }
    }
}
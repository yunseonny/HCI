package com.example.temiquicktest.temiui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.temiquicktest.AppViewModel
import com.example.temiquicktest.robot.GoToStatus

@Composable
fun MovingScreen(vm: AppViewModel, target: String, onCancel: () -> Unit) {
    val status by vm.status.collectAsState()
    val current by vm.target.collectAsState()

    // 화면 진입 시 한 번 이동 시작
    LaunchedEffect(target) { if (target.isNotBlank()) vm.goTo(target) }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (status) {
                GoToStatus.REACHED -> "도착했습니다!"
                GoToStatus.ABORT   -> "이동이 중단되었습니다"
                GoToStatus.START, GoToStatus.CALCULATING, GoToStatus.GOING -> "목적지로 이동 중입니다!"
                else -> "준비 중…"
            },
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text("목적지: $target", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = onCancel) { Text("안내 취소") }
    }
}
package com.example.temiquicktest.temiui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onGuideEntrance: () -> Unit,
    onGuideMonitor:  () -> Unit,
    onGuideCorner:   () -> Unit,
    onInfoClick: () -> Unit,
    onPhotoClick: () -> Unit,
    onPlayClick: () -> Unit,
    onGuestbookClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onGuideEntrance, modifier = Modifier.fillMaxWidth()) { Text("입구로 안내") }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onGuideMonitor,  modifier = Modifier.fillMaxWidth()) { Text("모니터로 안내") }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onGuideCorner,   modifier = Modifier.fillMaxWidth()) { Text("구석으로 안내") }

        // ↓ 이후 필요하면 기존 카드들(onInfoClick 등) 붙이면 됨
    }
}

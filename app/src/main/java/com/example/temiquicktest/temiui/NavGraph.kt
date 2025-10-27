package com.example.temiquicktest.temiui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.temiquicktest.AppViewModel
import com.example.temiquicktest.Locations
import com.example.temiquicktest.temiui.screen.HomeScreen
import com.example.temiquicktest.temiui.screen.MovingScreen

object Routes {
    const val HOME = "home"
    const val MOVING = "moving"
    const val MOVING_WITH_ARG = "moving/{target}"
}

@Composable
fun AppNavGraph(nav: NavHostController, vm: AppViewModel) {
    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onGuideEntrance = { nav.navigate("${Routes.MOVING}/${Locations.ENTRANCE}") },
                onGuideMonitor  = { nav.navigate("${Routes.MOVING}/${Locations.MONITOR}") },
                onGuideCorner   = { nav.navigate("${Routes.MOVING}/${Locations.CORNER}") },
                onInfoClick = { /* TODO */ },
                onPhotoClick = { /* TODO */ },
                onPlayClick = { /* TODO */ },
                onGuestbookClick = { /* TODO */ },
            )
        }
        composable(Routes.MOVING_WITH_ARG) { backStackEntry ->
            val target = backStackEntry.arguments?.getString("target") ?: ""
            MovingScreen(
                vm = vm,
                target = target,
                onCancel = {
                    vm.cancelGuide()
                    nav.popBackStack()
                }
            )
        }
    }
}
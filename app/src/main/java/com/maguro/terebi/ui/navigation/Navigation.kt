package com.maguro.terebi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maguro.terebi.ui.screens.schedule.ScheduleScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Schedule.route) {

        composable(
            route = Route.Schedule.route,
            arguments = Route.Schedule.arguments
        ) {
            ScheduleScreen()
        }

    }

}
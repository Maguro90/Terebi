package com.maguro.terebi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maguro.terebi.ui.screens.schedule.ScheduleScreen
import com.maguro.terebi.ui.screens.show_details.DetailsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Schedule.route) {

        composable(
            route = Route.Schedule.route,
            arguments = Route.Schedule.arguments
        ) {
            ScheduleScreen(
                onScheduleItemClick = {
                    navController.navigate(
                        Route.ShowDetails.routeWithArgs(it.id)
                    )
                }
            )
        }

        composable(
            route = Route.ShowDetails.route,
            arguments = Route.ShowDetails.arguments
        ) {
            DetailsScreen(onBackClick = { navController.popBackStack() })
        }

    }

}
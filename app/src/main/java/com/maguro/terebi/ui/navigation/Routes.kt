package com.maguro.terebi.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.maguro.terebi.data.model.Id

interface Route {

    val route: String
    val arguments: List<NamedNavArgument>

    object Schedule : Route {
        override val route = "schedule"
        override val arguments = emptyList<NamedNavArgument>()
    }

    object ShowDetails : Route {

        enum class Args(val pathName: String) {
            ScheduleItemId("scheduleItemId")
        }

        override val route =
            "show_details/{${Args.ScheduleItemId.pathName}}"

        override val arguments = listOf(
            navArgument(Args.ScheduleItemId.pathName) { NavType.StringType },
        )

        fun routeWithArgs(
            scheduleItemId: Id,
        ): String {
            return route
                .replace("{${Args.ScheduleItemId.pathName}}", scheduleItemId.toString())
        }
    }

    object Search : Route {
        override val route: String = "search"
        override val arguments: List<NamedNavArgument> = emptyList()
    }

}
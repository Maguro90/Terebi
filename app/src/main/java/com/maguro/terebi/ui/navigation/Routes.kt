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
            ShowId("showId"),
            EpisodeId("episodeId")
        }

        override val route =
            "show_details/{${Args.ShowId.pathName}}/episode/{${Args.EpisodeId.pathName}}"

        override val arguments = listOf(
            navArgument(Args.ShowId.name) { NavType.StringType },
            navArgument(Args.EpisodeId.name) { NavType.StringType }
        )

        fun routeWithArgs(
            showId: Id,
            episodeId: Id
        ): String {
            return route
                .replace("{${Args.ShowId.pathName}}", showId.toString())
                .replace("{${Args.EpisodeId.pathName}}", episodeId.toString())
        }
    }

}
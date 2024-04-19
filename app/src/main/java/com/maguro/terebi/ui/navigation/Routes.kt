package com.maguro.terebi.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Route {

    val route: String
    val arguments: List<NamedNavArgument>

    object Schedule : Route {
        override val route = "schedule"
        override val arguments = emptyList<NamedNavArgument>()
    }

    object ShowDetails : Route {
        override val route = "show_details/{showId}"
        override val arguments = listOf(
            navArgument("showId") { NavType.StringType }
        )
    }

}
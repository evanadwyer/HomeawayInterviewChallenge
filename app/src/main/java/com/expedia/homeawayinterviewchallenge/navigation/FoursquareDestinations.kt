package com.expedia.homeawayinterviewchallenge.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface FoursquareDestination {
    val route: String
}

object Main : FoursquareDestination {
    override val route = "main"
}

object Details : FoursquareDestination {
    override val route = "details"
    const val detailsIdArg = "details_id"
    val routeWithArgs = "${route}/{${detailsIdArg}}"
    val arguments = listOf(
        navArgument(detailsIdArg) { type = NavType.StringType }
    )
}
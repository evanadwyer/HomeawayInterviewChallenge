package com.expedia.homeawayinterviewchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.expedia.homeawayinterviewchallenge.ui.Details
import com.expedia.homeawayinterviewchallenge.ui.Main

@Composable
fun FoursquareNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Main.route
    ) {
        composable(route = Main.route) {
            Main(
                onPlaceClicked = { placeId ->
                    navController.navigateToDetails(placeId)
                }
            )
        }
        composable(
            route = Details.routeWithArgs,
            arguments = Details.arguments
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(Details.detailsIdArg)?.let { foursquareId ->
                Details(id = foursquareId)
            }
        }
    }
}

private fun NavHostController.navigateToDetails(placeId: String) {
    this.navigateSingleTopTo("${Details.route}/$placeId")
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
package com.example.nycschoolsapp.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nycschoolsapp.ui.composables.DetailsScreen
import com.example.nycschoolsapp.ui.composables.HomeScreen

private const val ARGS_SCHOOL_ID = "school_id"

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, startDestination = Destinations.HomeScreen.route
    ) {

        composable(Destinations.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(
            Destinations.DetailsScreen.route + "/{$ARGS_SCHOOL_ID}",
            arguments = listOf(navArgument(ARGS_SCHOOL_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(DestinationConstants.SCHOOL_ID)
                ?.let { id -> DetailsScreen(id) }
        }
    }

}

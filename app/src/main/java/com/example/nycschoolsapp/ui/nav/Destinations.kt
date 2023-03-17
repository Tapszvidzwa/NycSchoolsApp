package com.example.nycschoolsapp.ui.nav

sealed class Destinations(val route: String) {
    object HomeScreen : Destinations(route = DestinationConstants.HOME_SCREEN)
    object DetailsScreen : Destinations(route = DestinationConstants.DETAILS_SCREEN)
}

class DestinationConstants {
    companion object {
        const val HOME_SCREEN = "home_screen"
        const val DETAILS_SCREEN = "details_screen"
        const val SCHOOL_ID = "school_id"
    }
}

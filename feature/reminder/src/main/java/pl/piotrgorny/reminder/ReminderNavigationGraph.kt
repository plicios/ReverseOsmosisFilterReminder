package pl.piotrgorny.reminder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.reminderNavigationGraph(navController: NavHostController){
    navigation(
        route = "reminder",
        startDestination = "calendar"
    ) {
        composable(
            route = "calendar"
        ) {
            CalendarView(ViewType.Months)
        }
    }
}

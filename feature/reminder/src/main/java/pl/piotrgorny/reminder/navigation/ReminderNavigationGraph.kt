package pl.piotrgorny.reminder.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.piotrgorny.reminder.view.CalendarView

fun NavGraphBuilder.reminderNavigationGraph(navController: NavHostController){
    navigation(
        route = "reminder",
        startDestination = "calendar"
    ) {
        composable(
            route = "calendar"
        ) {
            CalendarView()
        }
    }
}

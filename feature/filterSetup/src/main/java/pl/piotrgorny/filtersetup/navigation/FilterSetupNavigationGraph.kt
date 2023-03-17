package pl.piotrgorny.filtersetup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.piotrgorny.filtersetup.screen.AddFilterSetupScreen
import pl.piotrgorny.filtersetup.screen.FilterSetupsScreen
import pl.piotrgorny.filtersetup.view.AddFilterSetupView
import pl.piotrgorny.filtersetup.view.FilterSetupDetailsView

fun NavGraphBuilder.filterSetupNavigationGraph(navController: NavHostController){
    navigation(
        route = "filterSetup",
        startDestination = "filterSetups"
    ) {
        composable(route = "filterSetups") {
            FilterSetupsScreen(
                navigateToAddFilterSetup = { navController.navigate("addFilterSetup") },
                navigateToFilterSetupDetails = { navController.navigate("filterSetupDetails") }
            )
        }
        composable(route = "addFilterSetup") {
            AddFilterSetupScreen {
                navController.popBackStack("filterSetups", false)
            }
        }
        composable(route = "filterSetupDetails") {
            FilterSetupDetailsView()
        }
    }
}
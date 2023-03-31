package pl.piotrgorny.filtersetup.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
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
                navigateToFilterSetupDetails = { navController.navigate("filterSetupDetails/${it.id}") }
            )
        }
        composable(route = "addFilterSetup") {
            AddFilterSetupScreen {
                navController.popBackStack("filterSetups", false)
            }
        }
        composable(
            route = "filterSetupDetails/{filterSetupId}",
            arguments = listOf(
                navArgument("filterSetupId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("filterSetupId")?.let {
                FilterSetupDetailsView(it)
            }
        }
    }
}
package pl.piotrgorny.filtersetup.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.extensions.toDate
import pl.piotrgorny.filtersetup.screen.AddFilterSetupScreen
import pl.piotrgorny.filtersetup.screen.FilterSetupsScreen
import pl.piotrgorny.filtersetup.view.AddOrModifyFilterDialog
import pl.piotrgorny.filtersetup.view.FilterSetupDetailsView
import pl.piotrgorny.model.Filter
import pl.piotrgorny.navigation.GetResult
import pl.piotrgorny.navigation.popBackStackWithResult

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
            AddFilterSetupScreen(
                navigateBackToList = { navController.popBackStack("filterSetups", false) },
                openAddOrModifyFilterDialog = {
                    if (it != null)
                        navController.navigate(
                            "addOrModifyFilter?" +
                                    "type=${it.type.name}" +
                                    "&installationDate=${it.installationDate.print()}" +
                                    "&lifeSpan=${it.lifeSpan.name}"
                        )
                    else
                        navController.navigate("addOrModifyFilter")
                },
                getFilterChanges = {
                    navController.GetResult<Pair<Filter?, Filter?>>(key = "filter") {result ->
                        it(result.first, result.second)
                    }
                }
            )
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
        dialog(
            route = "addOrModifyFilter?type={type}&installationDate={installationDate}&lifeSpan={lifeSpan}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("installationDate") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("lifeSpan") {
                    type = NavType.StringType
                    nullable = true
                },
            )
        ) { backStackEntry ->
            val type = backStackEntry.get("type", Filter.Type::valueOf)
            val installationDate = backStackEntry.get("installationDate", String::toDate)
            val lifeSpan = backStackEntry.get("lifeSpan", Filter.LifeSpan::valueOf)
            AddOrModifyFilterDialog(
                type = type,
                installationDate = installationDate,
                lifeSpan = lifeSpan,
                onDismiss = { navController.popBackStack() },
                onFilterRemoved = {
                    if(type != null && installationDate != null && lifeSpan!= null)
                        navController.popBackStackWithResult("filter", Filter(type, installationDate, lifeSpan) to null)
                    else
                        navController.popBackStack()
                  },
                onFilterAddedOrModified = {
                    if(type != null && installationDate != null && lifeSpan!= null)
                        navController.popBackStackWithResult("filter", Filter(type, installationDate, lifeSpan) to it)
                    else
                        navController.popBackStackWithResult("filter", null to it)
                }
            )
        }
    }
}

fun <T> NavBackStackEntry.get(key: String, converter: (String) -> T) = arguments?.getString(key)?.let(converter)

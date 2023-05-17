package pl.piotrgorny.filtersetup.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import pl.piotrgorny.common.toLocalDate
import pl.piotrgorny.common.toStringFromPattern
import pl.piotrgorny.filtersetup.FilterChange
import pl.piotrgorny.filtersetup.screen.AddOrModifyFilterSetupScreen
import pl.piotrgorny.filtersetup.screen.FilterSetupsScreen
import pl.piotrgorny.filtersetup.screen.RenewFiltersScreen
import pl.piotrgorny.filtersetup.view.AddOrModifyFilterDialog
import pl.piotrgorny.filtersetup.view.RemoveFilterDialog
import pl.piotrgorny.filtersetup.view.RemoveFilterSetupDialog
import pl.piotrgorny.model.Filter
import pl.piotrgorny.navigation.GetResult
import pl.piotrgorny.navigation.GetResultOnce
import pl.piotrgorny.navigation.popBackStackWithResult

fun NavGraphBuilder.filterSetupNavigationGraph(navController: NavHostController){
    navigation(
        route = "filterSetup",
        startDestination = "filterSetups"
    ) {
        composable(route = "filterSetups") {
            FilterSetupsScreen(
                navigateToAddFilterSetup = { navController.navigate("addOrModifyFilterSetup") },
                navigateToFilterSetupDetails = { navController.navigate("addOrModifyFilterSetup?filterSetupId=${it.id}") }
            )
        }
        composable(
            route = "addOrModifyFilterSetup?filterSetupId={filterSetupId}",
            arguments = listOf(
                navArgument("filterSetupId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val filterSetupId = backStackEntry.get("filterSetupId", String::toLongOrNull)
            AddOrModifyFilterSetupScreen(
                filterSetupId = filterSetupId,
                navigateBackToList = { navController.popBackStack("filterSetups", false) },
                openAddOrModifyFilterDialog = {index, filter ->
                    if (filter != null && index != null)
                        navController.navigate(
                            "addOrModifyFilter?" +
                                    "index=${index}" +
                                    "&type=${filter.type.name}" +
                                    "&installationDate=${filter.installationDate.toStringFromPattern()}" +
                                    "&lifeSpan=${filter.lifeSpan.name}"
                        )
                    else
                        navController.navigate("addOrModifyFilter")
                },
                openRemoveFilterDialog = {
                    navController.navigate("removeFilter/${it}")
                },
                openRemoveFilterSetupDialog = {
                    navController.navigate("removeFilterSetup/${it}")
                },
                openRenewFiltersView = {
                    navController.navigate("renewFilters/${it}")
                },
                getFilterChanges = {
                    navController.GetResult(key = "filterChange", onResult = it)
                },
                getFilterSetupRemoval = {
                    navController.GetResultOnce(key = "filterSetupDelete", it)
                }
            )
        }
        composable(
            route = "renewFilters/{filterSetupId}",
            arguments = listOf(
                navArgument("filterSetupId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("filterSetupId")?.let {
                RenewFiltersScreen(
                    filterSetupId = it,
                    navigateBackToFilterSetupDetails = {
                        navController.popBackStack()
                    }
                )
            }
        }
        dialog(
            route = "addOrModifyFilter?index={index}&type={type}&installationDate={installationDate}&lifeSpan={lifeSpan}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.StringType
                    nullable = true
                },
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
            val index = backStackEntry.get("index", String::toIntOrNull)
            val type = backStackEntry.get("type", Filter.Type::valueOf)
            val installationDate = backStackEntry.get("installationDate", String::toLocalDate)
            val lifeSpan = backStackEntry.get("lifeSpan", Filter.LifeSpan::valueOf)
            AddOrModifyFilterDialog(
                type = type,
                installationDate = installationDate,
                lifeSpan = lifeSpan,
                onDismiss = { navController.popBackStack() },
                onFilterRemoved = {
                    navController.popBackStackWithResult("filterChange", FilterChange(index))
                },
                onFilterAddedOrModified = {
                    navController.popBackStackWithResult("filterChange", FilterChange(index, it))
                }
            )
        }
        dialog(
            route = "removeFilter/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("index")?.let {
                RemoveFilterDialog(
                    onDismiss = {
                        navController.popBackStack()
                    },
                    onRemove = {
                        navController.popBackStackWithResult("filterChange", FilterChange(it))
                    }
                )
            }
        }
        dialog(
            route = "removeFilterSetup/{filterSetupId}",
            arguments = listOf(
                navArgument("filterSetupId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("filterSetupId")?.let {
                RemoveFilterSetupDialog(
                    onDismiss = {
                        navController.popBackStack()
                    },
                    onRemove = {
                        navController.popBackStackWithResult("filterSetupDelete", it)
                    }
                )
            }
        }
    }
}

fun <T> NavBackStackEntry.get(key: String, converter: (String) -> T) = arguments?.getString(key)?.let(converter)

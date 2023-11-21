package pl.piotrgorny.filtersetup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import pl.piotrgorny.common.toLocalDate
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


fun NavGraphBuilder.filterSetupNavigationGraph(navController: NavHostController, otherNavigationTrees: @Composable () -> Unit = {}){
    navigation(
        route = NavigationRoutes.graphRoute,
        startDestination = NavigationRoutes.Screen.FilterSetups
    ) {
        composable(route = NavigationRoutes.Screen.FilterSetups) {
            FilterSetupsScreen(
                navigateToAddOrModifyFilterSetup = { navController.navigate(NavigationRoutes.Screen.AddOrModifyFilterSetup.getRouteForParams(
                    it?.id
                )) },
                otherNavigationTrees
            )
        }
        composable(
            route = NavigationRoutes.Screen.AddOrModifyFilterSetup.route,
            arguments = NavigationRoutes.Screen.AddOrModifyFilterSetup.arguments
        ) { backStackEntry ->
            val filterSetupId = backStackEntry.get(NavigationRoutes.Screen.AddOrModifyFilterSetup.filterSetupIdArgument, String::toLongOrNull)
            AddOrModifyFilterSetupScreen(
                filterSetupId = filterSetupId,
                navigateBackToList = { navController.popBackStack(NavigationRoutes.Screen.FilterSetups, false) },
                openAddOrModifyFilterDialog = {index, filter ->
                    navController.navigate(
                        NavigationRoutes.Screen.AddOrModifyFilter.getRouteForParams(filter, index)
                    )
                },
                openRemoveFilterDialog = {
                    navController.navigate(NavigationRoutes.Dialog.RemoveFilter.getRouteForParams(it))
                },
                openRemoveFilterSetupDialog = {
                    navController.navigate(NavigationRoutes.Dialog.RemoveFilterSetup.getRouteForParams(it))
                },
                openRenewFiltersView = {
                    navController.navigate(NavigationRoutes.Screen.RenewFilters.getRouteForParams(it))
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
            route = NavigationRoutes.Screen.RenewFilters.route,
            arguments = NavigationRoutes.Screen.RenewFilters.arguments
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(NavigationRoutes.Screen.RenewFilters.filterSetupIdArgument)?.let {
                RenewFiltersScreen(
                    filterSetupId = it,
                    navigateBackToFilterSetupDetails = {
                        navController.popBackStack()
                    }
                )
            }
        }
        dialog(
            route = NavigationRoutes.Screen.AddOrModifyFilter.route,
            arguments = NavigationRoutes.Screen.AddOrModifyFilter.arguments
        ) { backStackEntry ->
            val index = backStackEntry.get(NavigationRoutes.Screen.AddOrModifyFilter.indexArgument, String::toIntOrNull)
            val type = backStackEntry.get(NavigationRoutes.Screen.AddOrModifyFilter.typeArgument, Filter.Type::valueOf)
            val installationDate = backStackEntry.get(NavigationRoutes.Screen.AddOrModifyFilter.installationDateArgument, String::toLocalDate)
            val lifeSpan = backStackEntry.get(NavigationRoutes.Screen.AddOrModifyFilter.lifeSpanArgument, Filter.LifeSpan::valueOf)
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
            route = NavigationRoutes.Dialog.RemoveFilter.route,
            arguments = NavigationRoutes.Dialog.RemoveFilter.arguments
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(NavigationRoutes.Dialog.RemoveFilter.indexArgument)?.let {
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
            route = NavigationRoutes.Dialog.RemoveFilterSetup.route,
            arguments = NavigationRoutes.Dialog.RemoveFilterSetup.arguments
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(NavigationRoutes.Dialog.RemoveFilterSetup.filterSetupIdArgument)?.let {
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

package pl.piotrgorny.filtersetup.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import pl.piotrgorny.common.toStringFromPattern
import pl.piotrgorny.model.Filter

object NavigationRoutes {
    const val graphRoute = "filterSetup"
    internal object Screen {
        const val FilterSetups = "filterSetups"
        object AddOrModifyFilterSetup {
            private const val baseRoute = "addOrModifyFilterSetup"
            const val filterSetupIdArgument = "filterSetupId"
            const val route = "$baseRoute?$filterSetupIdArgument={$filterSetupIdArgument}"
            val arguments = listOf(
                navArgument(filterSetupIdArgument) {
                    type = NavType.StringType
                    nullable = true
                }
            )
            fun getRouteForParams(filterSetupId: Long?) = filterSetupId?.let {
                "$baseRoute?$filterSetupIdArgument=${it}"
            } ?: baseRoute
        }

        object AddOrModifyFilter {
            private const val baseRoute = "addOrModifyFilter"
            const val indexArgument = "index"
            const val typeArgument = "type"
            const val installationDateArgument = "installationDate"
            const val lifeSpanArgument = "lifeSpan"
            const val route = "$baseRoute?" +
                    "$indexArgument={$indexArgument}" +
                    "&$typeArgument={$typeArgument}" +
                    "&$installationDateArgument={$installationDateArgument}" +
                    "&$lifeSpanArgument={$lifeSpanArgument}"
            val arguments = listOf(
                navArgument(indexArgument) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(typeArgument) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(installationDateArgument) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(lifeSpanArgument) {
                    type = NavType.StringType
                    nullable = true
                }
            )

            fun getRouteForParams(filter: Filter?, index: Int?) = if (filter != null && index != null)
                "$baseRoute?" +
                        "$indexArgument=${index}" +
                        "&$typeArgument=${filter.type.name}" +
                        "&$installationDateArgument=${filter.installationDate.toStringFromPattern()}" +
                        "&$lifeSpanArgument=${filter.lifeSpan.name}"
            else
                baseRoute
        }

        object RenewFilters {
            private const val baseRoute = "renewFilters"
            const val filterSetupIdArgument = "filterSetupId"
            const val route = "$baseRoute/{$filterSetupIdArgument}"
            val arguments = listOf(
                navArgument(filterSetupIdArgument) {
                    type = NavType.LongType
                }
            )
            fun getRouteForParams(filterSetupId: Long) = "$baseRoute/$filterSetupId"
        }
    }
    internal object Dialog {
        object RemoveFilter {
            private const val baseRoute = "removeFilter"
            const val indexArgument = "index"
            const val route = "$baseRoute/{$indexArgument}"
            val arguments = listOf(
                navArgument(indexArgument) {
                    type = NavType.IntType
                }
            )
            fun getRouteForParams(index: Int) = "$baseRoute/$index"
        }

        object RemoveFilterSetup {
            private const val baseRoute = "removeFilterSetup"
            const val filterSetupIdArgument = "filterSetupId"
            const val route = "$baseRoute/{$filterSetupIdArgument}"
            val arguments = listOf(
                navArgument(filterSetupIdArgument) {
                    type = NavType.LongType
                }
            )
            fun getRouteForParams(filterSetupId: Long) = "$baseRoute/$filterSetupId"
        }
    }
}
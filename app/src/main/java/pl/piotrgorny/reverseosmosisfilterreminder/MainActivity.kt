package pl.piotrgorny.reverseosmosisfilterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.piotrgorny.filtersetup.navigation.NavigationRoutes as FilterSetupNavigationRoutes
import pl.piotrgorny.filtersetup.navigation.filterSetupNavigationGraph
import pl.piotrgorny.reminder.navigation.reminderNavigationGraph
import pl.piotrgorny.reverseosmosisfilterreminder.ui.theme.ReverseOsmosisFilterReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReverseOsmosisFilterReminderTheme {
                val navController = rememberNavController()
                Surface {
                    NavHost(navController = navController, startDestination = FilterSetupNavigationRoutes.graphRoute) {
                        filterSetupNavigationGraph(navController)
                        reminderNavigationGraph(navController)
                    }
                }
            }
        }
    }
}

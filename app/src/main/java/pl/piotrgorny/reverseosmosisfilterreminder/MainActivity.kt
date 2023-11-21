package pl.piotrgorny.reverseosmosisfilterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.lifecycle.coroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.database.DatabaseFilterEventsRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.data.database.DatabaseReminderRepository
import pl.piotrgorny.filtersetup.navigation.filterSetupNavigationGraph
import pl.piotrgorny.reminder.RemindersScheduler
import pl.piotrgorny.reminder.navigation.reminderNavigationGraph
import pl.piotrgorny.reverseosmosisfilterreminder.ui.theme.ReverseOsmosisFilterReminderTheme
import pl.piotrgorny.filtersetup.navigation.NavigationRoutes as FilterSetupNavigationRoutes
import pl.piotrgorny.reminder.navigation.NavigationRoutes as ReminderNavigationRoutes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val eventsRepository =
            DatabaseFilterEventsRepository(DatabaseFilterSetupRepository.instance(this@MainActivity))
        lifecycle.coroutineScope.launch {
            eventsRepository.observeEvents()
        }
        lifecycle.coroutineScope.launch {
            RemindersScheduler(
                eventsRepository,
                DatabaseReminderRepository(this@MainActivity)
            ).observeChanges()
        }
        setContent {
            ReverseOsmosisFilterReminderTheme {
                val navController = rememberNavController()
                Surface {
                    NavHost(navController = navController, startDestination = FilterSetupNavigationRoutes.graphRoute) {
                        filterSetupNavigationGraph(navController) {
                            IconButton(onClick = { navController.navigate(ReminderNavigationRoutes.graphRoute) }) {
                                Icon(Icons.Filled.DateRange, "reminders graph")
                            }
                        }
                        reminderNavigationGraph(navController)
                    }
                }
            }
        }
    }
}
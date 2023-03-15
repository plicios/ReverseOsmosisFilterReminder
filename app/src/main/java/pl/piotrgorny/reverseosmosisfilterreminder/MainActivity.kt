package pl.piotrgorny.reverseosmosisfilterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.piotrgorny.filtersetup.navigation.filterSetupNavigationGraph
import pl.piotrgorny.reverseosmosisfilterreminder.ui.theme.ReverseOsmosisFilterReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReverseOsmosisFilterReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "filterSetup") {
                    filterSetupNavigationGraph(navController)
                }
            }
        }
    }
}

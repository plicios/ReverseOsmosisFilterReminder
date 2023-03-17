package pl.piotrgorny.reverseosmosisfilterreminder

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
                Surface(modifier = Modifier.background(MaterialTheme.colors.surface)) {
                    NavHost(navController = navController, startDestination = "filterSetup") {
                        filterSetupNavigationGraph(navController)
                    }
                }

            }
        }
    }
}

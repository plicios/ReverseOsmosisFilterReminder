package pl.piotrgorny.filtersetup

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.filtersetup.view.FilterSetupsView
import pl.piotrgorny.filtersetup.viewModel.FilterSetupViewModel

@Composable
fun FilterSetupsScreen() {
    val viewModel: FilterSetupViewModel = viewModel()
    val state = viewModel.viewState.value
    FilterSetupsView(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event)},
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect){
                is FilterSetupsContract.Effect.Navigation.ToFilterSetupDetails -> {
                    //Todo navigate
                }
            }
        }
    )
}
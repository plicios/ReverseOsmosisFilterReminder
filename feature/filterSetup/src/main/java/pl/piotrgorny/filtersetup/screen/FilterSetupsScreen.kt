package pl.piotrgorny.filtersetup.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.filtersetup.view.FilterSetupsView
import pl.piotrgorny.filtersetup.viewModel.FilterSetupsViewModel
import pl.piotrgorny.model.FilterSetup

@Composable
fun FilterSetupsScreen(
    navigateToAddFilterSetup: () -> Unit,
    navigateToFilterSetupDetails: (FilterSetup) -> Unit
) {
    val viewModel: FilterSetupsViewModel = viewModel(factory = FilterSetupsViewModel.Factory(
        LocalContext.current))
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is FilterSetupsContract.Effect.ToastDataWasLoaded -> {  }
                is FilterSetupsContract.Effect.Navigation.ToFilterSetupDetails ->
                    navigateToFilterSetupDetails(effect.filterSetup)
                is FilterSetupsContract.Effect.Navigation.ToAddFilterSetup ->
                    navigateToAddFilterSetup()
            }
        }.collect()
    }

    FilterSetupsView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
}
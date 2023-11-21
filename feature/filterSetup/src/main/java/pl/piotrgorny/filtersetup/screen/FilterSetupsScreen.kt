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
    navigateToAddOrModifyFilterSetup: (FilterSetup?) -> Unit,
    otherNavigationTrees: @Composable () -> Unit = {}
) {
    val viewModel: FilterSetupsViewModel = viewModel(factory = FilterSetupsViewModel.Factory(
        LocalContext.current))
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is FilterSetupsContract.Effect.Navigation.ToFilterSetupDetails ->
                    navigateToAddOrModifyFilterSetup(effect.filterSetup)
                is FilterSetupsContract.Effect.Navigation.ToAddFilterSetup ->
                    navigateToAddOrModifyFilterSetup(null)
            }
        }.collect()
    }

    FilterSetupsView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) },
        otherNavigationTrees
    )
}
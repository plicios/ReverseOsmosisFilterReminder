package pl.piotrgorny.filtersetup.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.RenewFiltersContract
import pl.piotrgorny.filtersetup.view.RenewFiltersView
import pl.piotrgorny.filtersetup.viewModel.RenewFiltersViewModel

@Composable
fun RenewFiltersScreen(
    filterSetupId: Long,
    navigateBackToFilterSetupDetails: () -> Unit = {},
) {
    val viewModel: RenewFiltersViewModel = viewModel(factory = RenewFiltersViewModel.Factory(
        filterSetupId,
        LocalContext.current)
    )

    val state = viewModel.viewState.value

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                RenewFiltersContract.Effect.FiltersRenewed ->
                    navigateBackToFilterSetupDetails()
            }
        }.collect()
    }

    RenewFiltersView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
}
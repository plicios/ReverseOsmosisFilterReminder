package pl.piotrgorny.filtersetup.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.AddFilterSetupContract
import pl.piotrgorny.filtersetup.view.AddFilterSetupView
import pl.piotrgorny.filtersetup.viewModel.AddFilterSetupViewModel

@Composable
fun AddFilterSetupScreen(navigateBackToList: () -> Unit) {
    val viewModel: AddFilterSetupViewModel = viewModel(factory = AddFilterSetupViewModel.Factory())
    val state = viewModel.viewState.value

    LaunchedEffect("key") {//TODO add key
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AddFilterSetupContract.Effect.ToastDataWasLoaded -> {  }
                is AddFilterSetupContract.Effect.Navigation.BackToFilterSetups ->
                    navigateBackToList()
            }
        }.collect()
    }

    AddFilterSetupView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
}
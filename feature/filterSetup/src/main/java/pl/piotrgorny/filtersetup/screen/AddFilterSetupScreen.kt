package pl.piotrgorny.filtersetup.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.AddFilterSetupContract
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.view.AddFilterSetupView
import pl.piotrgorny.filtersetup.viewModel.AddFilterSetupViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.navigation.GetResult

@Composable
fun AddFilterSetupScreen(
    navigateBackToList: () -> Unit = {},
    openAddOrModifyFilterDialog: (oldFilter: Filter?) -> Unit = {},
    getFilterChanges: @Composable (@Composable (Filter?, Filter?) -> Unit) -> Unit
) {
    val viewModel: AddFilterSetupViewModel = viewModel(factory = AddFilterSetupViewModel.Factory(
        LocalContext.current))
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AddFilterSetupContract.Effect.ToastDataWasLoaded -> {  }
                is AddFilterSetupContract.Effect.Navigation.BackToFilterSetups ->
                    navigateBackToList()
                is AddFilterSetupContract.Effect.Navigation.OpenAddOrModifyFilterDialog -> {
                    openAddOrModifyFilterDialog(effect.oldFilter)
                }
            }
        }.collect()
    }

    AddFilterSetupView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
    getFilterChanges { oldFilter, newFilter ->
        LaunchedEffect(oldFilter.toString() + newFilter.toString()) {
            when{
                oldFilter != null && newFilter != null ->
                    viewModel.setEvent(AddFilterSetupContract.Event.ModifyFilter(oldFilter, newFilter))
                newFilter != null ->
                    viewModel.setEvent(AddFilterSetupContract.Event.AddFilter(newFilter))
                oldFilter != null ->
                    viewModel.setEvent(AddFilterSetupContract.Event.RemoveFilter(oldFilter))
            }
        }
    }
}
package pl.piotrgorny.filtersetup.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.FilterChange
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterSetupContract
import pl.piotrgorny.filtersetup.view.AddOrModifyFilterSetupView
import pl.piotrgorny.filtersetup.viewModel.AddOrModifyFilterSetupViewModel
import pl.piotrgorny.model.Filter

@Composable
fun AddOrModifyFilterSetupScreen(
    filterSetupId: Long? = null,
    navigateBackToList: () -> Unit = {},
    openAddOrModifyFilterDialog: (index: Int?, filter: Filter?) -> Unit = { _, _ -> },
    openRemoveFilterDialog: (index: Int) -> Unit = {},
    openRemoveFilterSetupDialog: (filterSetupId: Long) -> Unit = {},
    openRenewFiltersView: (filterSetupId: Long) -> Unit = {},
    getFilterChanges: @Composable (@Composable (FilterChange) -> Unit) -> Unit = {},
    getFilterSetupRemoval: @Composable (@Composable (Long) -> Unit) -> Unit = {}
) {
    val viewModel: AddOrModifyFilterSetupViewModel = viewModel(factory = AddOrModifyFilterSetupViewModel.Factory(
        filterSetupId,
        LocalContext.current)
    )
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AddOrModifyFilterSetupContract.Effect.Navigation.BackToFilterSetups ->
                    navigateBackToList()
                is AddOrModifyFilterSetupContract.Effect.Navigation.OpenAddOrModifyFilterDialog ->
                    openAddOrModifyFilterDialog(effect.index, effect.filter)
                is AddOrModifyFilterSetupContract.Effect.Navigation.OpenRemoveFilterDialog ->
                    openRemoveFilterDialog(effect.index)
                is AddOrModifyFilterSetupContract.Effect.Navigation.OpenRemoveFilterSetupDialog ->
                    openRemoveFilterSetupDialog(effect.filterSetupId)
                is AddOrModifyFilterSetupContract.Effect.Navigation.OpenRenewFiltersView ->
                    openRenewFiltersView(effect.filterSetupId)
            }
        }.collect()
    }

    AddOrModifyFilterSetupView(
        state = state,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
    getFilterChanges {
        LaunchedEffect(it.toString()) {
            when{
                it.newValue != null && it.index != null ->
                    viewModel.setEvent(AddOrModifyFilterSetupContract.Event.ModifyFilter(it.index, it.newValue))
                it.index != null ->
                    viewModel.setEvent(AddOrModifyFilterSetupContract.Event.RemoveFilter(it.index))
                it.newValue != null->
                    viewModel.setEvent(AddOrModifyFilterSetupContract.Event.AddFilter(it.newValue))
            }
        }
    }

    getFilterSetupRemoval {
        LaunchedEffect(it) {
            viewModel.setEvent(AddOrModifyFilterSetupContract.Event.RemoveFilterSetup)
        }
    }
}
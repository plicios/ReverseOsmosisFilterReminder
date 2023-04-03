package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.ModifyFilterContract
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.viewModel.ModifyFilterViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.date.DateField
import pl.piotrgorny.ui.dialog.Dialog
import pl.piotrgorny.ui.dropdown.Dropdown


@Composable
fun ModifyFilterDialog(
    filter: Filter,
    onDismiss: () -> Unit,
    onFilterRemoved: (Filter) -> Unit,
    onFilterModified: (newFilter: Filter, oldFilter: Filter) -> Unit
) {
    val viewModel: ModifyFilterViewModel = viewModel(factory = ModifyFilterViewModel.Factory(filter))

    LaunchedEffect("key") {//TODO add key
        viewModel.effect.onEach { effect ->
            when (effect) {
                is ModifyFilterContract.Effect.FilterModified -> onFilterModified(effect.newFilter, effect.oldFilter)
                is ModifyFilterContract.Effect.FilterRemoved -> onFilterRemoved(effect.filter)
            }
        }.collect()
    }

    val state = viewModel.viewState.value

    Dialog(
        title = "AddFilter",
        confirmButtonTitle = "Add",
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.handleEvents(ModifyFilterContract.Event.ModifyFilter)
        }
    ) {
        Dropdown(
            label = "Type",
            defaultValue = state.type,
            options = Filter.Type.values(),
            optionToString = Filter.Type::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(ModifyFilterContract.Event.TypeChange(it))
            }
        )
        DateField(
            label = "Installation date",
            initialDate = state.installationDate,
            onDateChange = {
                viewModel.handleEvents(ModifyFilterContract.Event.InstallationDateChange(it))
            }
        )
        Dropdown(
            label = "Lifespan",
            defaultValue = state.lifeSpan,
            options = Filter.LifeSpan.values().toList(),
            optionToString = Filter.LifeSpan::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(ModifyFilterContract.Event.LifeSpanChange(it))
            }
        )
    }
}
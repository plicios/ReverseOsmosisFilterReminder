package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterContract
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.viewModel.AddOrModifyFilterViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.date.DateField
import pl.piotrgorny.ui.dialog.Dialog
import pl.piotrgorny.ui.dropdown.Dropdown

@Composable
fun AddOrModifyFilterDialog(
    type: Filter.Type?,
    installationDate: LocalDate?,
    lifeSpan: Filter.LifeSpan?,
    onDismiss: () -> Unit,
    onFilterRemoved: () -> Unit,
    onFilterAddedOrModified: (Filter) -> Unit
) {
    val viewModel: AddOrModifyFilterViewModel = viewModel(factory = AddOrModifyFilterViewModel.Factory(type, installationDate, lifeSpan))

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AddOrModifyFilterContract.Effect.FilterAddedOrModified -> onFilterAddedOrModified(effect.filter)
                is AddOrModifyFilterContract.Effect.FilterRemoved -> onFilterRemoved()
            }
        }.collect()
    }

    val state = viewModel.viewState.value

    Dialog(
        title = if(state.stateType == AddOrModifyFilterContract.State.Type.Add) "Add filter" else "Edit filter",
        confirmButtonTitle = if(state.stateType == AddOrModifyFilterContract.State.Type.Add) "Add" else "Edit",
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.handleEvents(AddOrModifyFilterContract.Event.AddOrModifyFilter)
        }
    ) {
        Dropdown(
            label = "Type",
            defaultValue = state.type,
            options = Filter.Type.values(),
            optionToString = Filter.Type::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.TypeChange(it))
            },
            error = state.getError("typeError")?.name
        )
        DateField(
            label = "Installation date",
            initialDate = state.installationDate?.toDate(),
            onDateChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.InstallationDateChange(LocalDate(it)))
            },
            error = state.getError("installationDateError")?.name
        )
        Dropdown(
            label = "Lifespan",
            defaultValue = state.lifeSpan,
            options = Filter.LifeSpan.values().toList(),
            optionToString = Filter.LifeSpan::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.LifeSpanChange(it))
            },
            error = state.getError("lifeSpanError")?.name
        )
    }
}
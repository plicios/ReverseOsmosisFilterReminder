package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.R
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
        title = if(state.stateType == AddOrModifyFilterContract.State.Type.Add)
            stringResource(id = R.string.add_filter)
        else
            stringResource(id = R.string.edit_filter),
        confirmButtonTitle = if(state.stateType == AddOrModifyFilterContract.State.Type.Add)
            stringResource(id = R.string.add)
        else
            stringResource(id = R.string.edit),
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.handleEvents(AddOrModifyFilterContract.Event.AddOrModifyFilter)
        }
    ) {
        Dropdown(
            label = stringResource(id = R.string.type),
            defaultValue = state.type,
            options = Filter.Type.values(),
            optionToString = { it.print() },
            onSelectedOptionChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.TypeChange(it))
            },
            error = state.getError("typeError")?.name
        )
        DateField(
            label = stringResource(id = R.string.installation_date),
            initialDate = state.installationDate?.toDate(),
            onDateChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.InstallationDateChange(LocalDate(it)))
            },
            error = state.getError("installationDateError")?.name
        )
        Dropdown(
            label = stringResource(id = R.string.lifespan),
            defaultValue = state.lifeSpan,
            options = Filter.LifeSpan.values().toList(),
            optionToString = { it.print() },
            onSelectedOptionChange = {
                viewModel.handleEvents(AddOrModifyFilterContract.Event.LifeSpanChange(it))
            },
            error = state.getError("lifeSpanError")?.name
        )
    }
}
package pl.piotrgorny.filtersetup.view

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import pl.piotrgorny.filtersetup.contract.AddFilterContract
import pl.piotrgorny.filtersetup.viewModel.AddFilterViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.date.DateField
import pl.piotrgorny.ui.dialog.Dialog
import pl.piotrgorny.ui.dropdown.Dropdown
import pl.piotrgorny.ui.dropdown.EnumDropDownItem

@Composable
fun AddFilterDialog(onDismiss: () -> Unit, onFilterAdded: (Filter) -> Unit) {
    val viewModel: AddFilterViewModel = viewModel()

    LaunchedEffect("key") {//TODO add key
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AddFilterContract.Effect.FilterAdded -> onFilterAdded(effect.filter)
            }
        }.collect()
    }

    val state = viewModel.viewState.value

    Dialog(
        title = "AddFilter",
        confirmButtonTitle = "Add",
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.handleEvents(AddFilterContract.Event.AddFilter)
        }
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = {
                viewModel.handleEvents(AddFilterContract.Event.NameChange(it))
            },
            label = {
                Text(text = "Name")
            }
        )
        Dropdown(
            label = "Type",
            defaultValue = state.type?.name ?: "",
            options = Filter.Type.values().map { EnumDropDownItem(it) },
            onSelectedOptionChange = {
                viewModel.handleEvents(AddFilterContract.Event.TypeChange(it))
            }
        )
        DateField(
            label = "Installation date",
            onDateChange = {
                viewModel.handleEvents(AddFilterContract.Event.InstallationDateChange(it))
            }
        )
        Dropdown(
            label = "Lifespan",
            defaultValue = state.lifeSpan?.name ?: "",
            options = Filter.LifeSpan.values().map { EnumDropDownItem(it) },
            onSelectedOptionChange = {
                viewModel.handleEvents(AddFilterContract.Event.LifeSpanChange(it))
            }
        )
    }
}
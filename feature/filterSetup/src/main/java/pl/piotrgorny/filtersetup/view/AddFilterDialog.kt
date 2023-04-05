package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.contract.AddFilterContract
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.viewModel.AddFilterViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.date.DateField
import pl.piotrgorny.ui.dialog.Dialog
import pl.piotrgorny.ui.dropdown.Dropdown

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
        Dropdown(
            label = "Type",
            defaultValue = state.type,
            options = Filter.Type.values(),
            optionToString =  Filter.Type::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(AddFilterContract.Event.TypeChange(it))
            }
        )
        DateField(
            label = "Installation date",
            initialDate = state.installationDate.toDate(),
            onDateChange = {
                viewModel.handleEvents(AddFilterContract.Event.InstallationDateChange(LocalDate(it)))
            }
        )
        Dropdown(
            label = "Lifespan",
            defaultValue = state.lifeSpan,
            options = Filter.LifeSpan.values().toList(),
            optionToString =  Filter.LifeSpan::print,
            onSelectedOptionChange = {
                viewModel.handleEvents(AddFilterContract.Event.LifeSpanChange(it))
            }
        )
    }
}
package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.contract.RenewFiltersContract
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.filtersetup.viewModel.RenewFiltersViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.date.DateField
import pl.piotrgorny.ui.dialog.Dialog


@Composable
fun RenewFiltersDialog(
    filterSetupId: Long,
    onDismiss: () -> Unit,
    onFiltersRenewed: () -> Unit,
) {
    val viewModel: RenewFiltersViewModel = viewModel(factory = RenewFiltersViewModel.Factory(filterSetupId, LocalContext.current))

    LaunchedEffect(viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                RenewFiltersContract.Effect.ToastDataWasLoaded -> TODO()
                RenewFiltersContract.Effect.Navigation.BackToFilterSetups ->
                    onFiltersRenewed()
            }
        }.collect()
    }

    val state = viewModel.viewState.value

    Dialog(
        title = "Renew filters",
        confirmButtonTitle = "Renew",
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.handleEvents(RenewFiltersContract.Event.RenewFilters)
        }
    ) {
        DateField(
            label = "Renewal date",
            initialDate = state.renewalDate.toDate(),
            onDateChange = {
                viewModel.handleEvents(RenewFiltersContract.Event.RenewalDateChanged(LocalDate(it)))
            }
        )
        LazyColumn {
            items(state.filterSelection.toList()) {
                FilterSelectionRow(it.first, it.second) { filter, selected ->
                    viewModel.setEvent(RenewFiltersContract.Event.FilterSelectionChange(filter, selected))
                }
            }
        }
    }
}

@Composable
fun FilterSelectionRow(filter: Filter, selected: Boolean, onSelectionChange: (Filter, Boolean) -> Unit) {
    Row {
        Column {
            Text(text = filter.type.print())
            Text(text = filter.installationDate.print())
            Text(text = filter.getExpirationDate().print())
        }
        Checkbox(checked = selected, onCheckedChange = { onSelectionChange(filter, it) })
    }
}
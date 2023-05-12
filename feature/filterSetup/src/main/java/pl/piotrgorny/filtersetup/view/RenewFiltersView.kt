package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.filtersetup.contract.RenewFiltersContract
import pl.piotrgorny.model.Filter

@Composable
fun RenewFiltersView(
    state: RenewFiltersContract.State,
    onEventSent: (RenewFiltersContract.Event) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEventSent(RenewFiltersContract.Event.RenewFilters) }) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "refresh selected filters"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                value = state.filterSetup?.name ?: "",
                onValueChange = {},
                label = { Text(text = "Name") }
            )
            LazyColumn {
                items(state.filterSelection) {
                    FilterSelectionRow(
                        filter = it.first,
                        selected = it.second,
                        onSelectionChange = { selected -> onEventSent(
                            if(selected)
                                RenewFiltersContract.Event.FilterSelected(it.first)
                            else
                                RenewFiltersContract.Event.FilterDeselected(it.first)
                        ) })
                }
            }
        }
    }
}

@Composable
fun FilterSelectionRow(filter: Filter, selected: Boolean, onSelectionChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp) {
        Row(modifier = Modifier.background(if(filter.isExpired) MaterialTheme.colors.error else MaterialTheme.colors.surface)) {
            FilterData(filter = filter, modifier = Modifier.weight(1f))
            Checkbox(checked = selected, onCheckedChange = onSelectionChange)
        }
    }
}
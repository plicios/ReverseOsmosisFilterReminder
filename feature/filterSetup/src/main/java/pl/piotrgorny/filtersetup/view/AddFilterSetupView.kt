package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.filtersetup.contract.AddFilterSetupContract
import pl.piotrgorny.model.Filter

@Composable
fun AddFilterSetupView(
    state: AddFilterSetupContract.State,
    onEventSent: (AddFilterSetupContract.Event) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = {
                onEventSent(AddFilterSetupContract.Event.NameChange(it))
            },
            label = { Text(text = "Name") }
        )
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Filters")
            IconButton(onClick = { onEventSent(AddFilterSetupContract.Event.RequestAddFilter) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add filter")
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(bottom = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.filters) {
                FilterRow(filter = it)
            }
        }
        Button(onClick = { onEventSent(AddFilterSetupContract.Event.AddFilterSetup) }) {
            Text(text = "Add filter setup")
        }
        if(state.isAddFilterDialogOpen){
            AddFilterDialog(
                onDismiss = {
                    onEventSent(AddFilterSetupContract.Event.DismissAddFilter)
                },
                onFilterAdded = {
                    onEventSent(AddFilterSetupContract.Event.AddFilter(it))
                }
            )
        }
    }
}

@Composable
fun FilterRow(filter: Filter) {
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = MaterialTheme.colors.surface, elevation = 10.dp) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(text = filter.name)
            Text(text = filter.type.toString())
            Text(text = filter.installationDate.toString())
            Text(text = filter.lifeSpan.toString())
        }
    }
}

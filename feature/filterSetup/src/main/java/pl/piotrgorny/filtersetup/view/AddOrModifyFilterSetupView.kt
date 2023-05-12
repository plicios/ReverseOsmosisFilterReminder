package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterSetupContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.ui.dropdown.Dropdown

@Composable
fun AddOrModifyFilterSetupView(
    state: AddOrModifyFilterSetupContract.State,
    onEventSent: (AddOrModifyFilterSetupContract.Event) -> Unit
) {
    Scaffold(topBar = {
        if(state.stateType.canAny()) {
            TopAppBar {
                if(state.stateType.canSwitchToEdit) {
                    IconButton(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.RequestModifyFilterSetup) }) {
                        Icon(Icons.Filled.Edit, "edit filter setup")
                    }
                }
                if(state.stateType.canDelete) {
                    IconButton(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.RequestRemoveFilterSetup) }) {
                        Icon(Icons.Filled.Delete, "delete filter setup")
                    }
                }
                if(state.stateType.canSwitchToRenew) {
                    IconButton(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.RequestRenewFilters) }) {
                        Icon(Icons.Filled.Refresh, "Renew filters")
                    }
                }
            }
        }
    }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                readOnly = state.stateType == AddOrModifyFilterSetupContract.State.Type.View,
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = {
                    onEventSent(AddOrModifyFilterSetupContract.Event.NameChange(it))
                },
                label = { Text(text = "Name") }
            )
            if (state.stateType.isEditable) {
                Dropdown(
                    label = "Type",
                    options = FilterSetup.Type.values(),
                    defaultValue = state.type,
                    optionToString = FilterSetup.Type::name
                ) {
                    onEventSent(AddOrModifyFilterSetupContract.Event.TypeChange(it))
                }
            } else {
                OutlinedTextField(
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = state.type.name,
                    label = { Text(text = "Type") },
                    onValueChange = {}
                )
            }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Filters")
                if(state.stateType.isEditable) {
                    IconButton(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.RequestAddFilter) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add filter")
                    }
                }
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(state.filters) {index, item ->
                    FilterRow(
                        filter = item,
                        readOnly = !state.stateType.isEditable,
                        onFilterEditPress = { filter ->
                            onEventSent(AddOrModifyFilterSetupContract.Event.RequestModifyFilter(index, filter))
                        },
                        onFilterRemovePress = {
                            onEventSent(AddOrModifyFilterSetupContract.Event.RequestRemoveFilter(index))
                        }
                    )
                }
            }
            if(state.stateType != AddOrModifyFilterSetupContract.State.Type.View){
                Button(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.AddOrModifyFilterSetup) }) {
                    if(state.stateType == AddOrModifyFilterSetupContract.State.Type.Add)
                        Text(text = "Add filter setup")
                    else
                        Text(text = "Modify filter setup")
                }
            }
        }
    }
}

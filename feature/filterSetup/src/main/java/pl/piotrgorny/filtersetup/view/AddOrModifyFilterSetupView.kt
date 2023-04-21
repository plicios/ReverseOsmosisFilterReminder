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
import androidx.compose.material.icons.filled.Edit
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
        if(state.stateType == AddOrModifyFilterSetupContract.State.Type.View){
            TopAppBar {
                IconButton(onClick = { onEventSent(AddOrModifyFilterSetupContract.Event.RequestModifyFilterSetup) }) {
                    Icon(Icons.Filled.Edit, "edit filter setup")
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
            if(state.stateType == AddOrModifyFilterSetupContract.State.Type.View) {
                OutlinedTextField(
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = state.type.name,
                    label = { Text(text = "Type") },
                    onValueChange = {}
                )
            } else {
                Dropdown(
                    label = "Type",
                    options = FilterSetup.Type.values(),
                    defaultValue = state.type,
                    optionToString = FilterSetup.Type::name
                ) {
                    onEventSent(AddOrModifyFilterSetupContract.Event.TypeChange(it))
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Filters")
                if(state.stateType != AddOrModifyFilterSetupContract.State.Type.View) {
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
                        readOnly = state.stateType == AddOrModifyFilterSetupContract.State.Type.View,
                        onFilterEditPress = { filter ->
                            onEventSent(AddOrModifyFilterSetupContract.Event.RequestModifyFilter(filter))
                        },
                        onFilterRemovePress = { filter ->
                            onEventSent(AddOrModifyFilterSetupContract.Event.RequestRemoveFilter(filter))
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

package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.model.FilterSetup

@Composable
fun FilterSetupsView(
    state: FilterSetupsContract.State,
    onEventSent: (event: FilterSetupsContract.Event) -> Unit
) {
    when {
        state.isLoading -> {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(text = "Loading filter setups")
            }
        } 
        state.filterSetups.isEmpty() -> {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "No filter setups available")
                Button(modifier = Modifier, onClick = { onEventSent(FilterSetupsContract.Event.AddFilterSetup) }) {
                    Text(text = "Add new")
                }
            }
        }
        else -> {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { onEventSent(FilterSetupsContract.Event.AddFilterSetup) }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add setup"
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) {
                Column(modifier = Modifier.padding(it)) {
                    FilterSetupsList(filterSetups = state.filterSetups) { filterSetup ->
                        onEventSent(FilterSetupsContract.Event.FilterSetupSelection(filterSetup))
                    }
                }
            }


        }
    }
}

@Composable
fun FilterSetupsList(filterSetups: List<FilterSetup>, onItemClicked: (FilterSetup) -> Unit) {
    LazyColumn {
        items(items = filterSetups) {
            FilterSetupRow(filterSetup = it, onItemClicked)
        }
    }
}

@Composable
fun FilterSetupRow(filterSetup: FilterSetup, onItemClicked: (FilterSetup) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onItemClicked(filterSetup) },
    ) {
        Row(Modifier.padding(15.dp)) {
            Text(text = filterSetup.name)
        }
    }
}
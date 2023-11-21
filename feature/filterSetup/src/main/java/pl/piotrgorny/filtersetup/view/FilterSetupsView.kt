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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.piotrgorny.filtersetup.R
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.ui.loader.Loader

@Composable
fun FilterSetupsView(
    state: FilterSetupsContract.State,
    onEventSent: (event: FilterSetupsContract.Event) -> Unit,
    otherNavigationTrees: @Composable () -> Unit = {}
) {
    when {
        state.isLoading -> {
            Loader(stringResource(id = R.string.loading_filter_setups))
        }
        state.filterSetups.isEmpty() -> {
            Scaffold(
                topBar = {
                    TopAppBar {
                        otherNavigationTrees()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.no_filter_setups))
                    Button(
                        modifier = Modifier,
                        onClick = { onEventSent(FilterSetupsContract.Event.AddFilterSetup) }) {
                        Text(text = stringResource(id = R.string.add_filter_setup))
                    }
                }
            }
        }
        else -> {
            Scaffold(
                topBar = {
                    TopAppBar {
                        otherNavigationTrees()
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { onEventSent(FilterSetupsContract.Event.AddFilterSetup) }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_filter_setup)
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) {
                Column(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(15.dp)) {
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
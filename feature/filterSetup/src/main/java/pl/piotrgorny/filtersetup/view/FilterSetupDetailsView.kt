package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.filtersetup.viewModel.FilterSetupDetailsViewModel
import pl.piotrgorny.ui.loader.Loader

@Composable
fun FilterSetupDetailsView(filterSetupId: Long) {
    val viewModel: FilterSetupDetailsViewModel = viewModel(factory = FilterSetupDetailsViewModel.Factory(
        LocalContext.current,
        filterSetupId
    ))
    val state = viewModel.viewState.value
    Scaffold {
        Column(
            Modifier.padding(it).fillMaxSize().padding(15.dp)
        ) {
            when {
                state.isLoading -> {
                    Loader("Loading filter setup details")
                }
                state.filterSetup != null -> {
                    Text(text = "name: ${state.filterSetup.name}")
                    Text(text = "type: ${state.filterSetup.type}")
                    LazyColumn {
                        items(state.filterSetup.filters) {
                            FilterRow(filter = it)
                        }
                    }
                }
            }
        }
    }
}
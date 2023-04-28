package pl.piotrgorny.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.joda.time.LocalDate


@Composable
fun CalendarView(viewType: ViewType) {
    val events = listOf(LocalDate(), LocalDate().minusDays(20))
    YearPicker()
    List()
//    when(viewType){
//        ViewType.Months -> TODO()
//        ViewType.Days -> TODO()
//    }
}



@Composable
fun List() {

    val viewModel = CalendarViewModel()
    val events = viewModel.getEvents().collectAsLazyPagingItems()


    LazyColumn {
        items(
            items = events,
            key = { it }
        ) { event ->
            Text(
                modifier = Modifier
                    .height(75.dp),
                text = event.toString(),
            )

            Divider()
        }

        when (val state = events.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }

        when (val state = events.loadState.append) { // Pagination
            is LoadState.Error -> {
                //TODO Pagination Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }
    }
}

enum class ViewType {
    Months,
    Days
}

@Composable
fun YearPicker(
    initialYear: Int = LocalDate().year,
    onYearChanged: (Int) -> Unit = {}
) {

}
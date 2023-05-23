package pl.piotrgorny.ui.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import pl.piotrgorny.ui.ErrorMessage
import pl.piotrgorny.ui.loader.Loader


@Composable
fun <T : Any> LazyPagingList(lazyPagingItems: LazyPagingItems<T>, row: @Composable (T) -> Unit) {
    LazyColumn {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey(),
            contentType = lazyPagingItems.itemContentType()
        ) { index ->
            val item = lazyPagingItems[index]
            item?.let {
                row(it)
            }
        }

        when (val state = lazyPagingItems.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    state.error.message?.let {
                        ErrorMessage(error = it)
                    }
                }
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

        when (val state = lazyPagingItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                item {
                    state.error.message?.let {
                        ErrorMessage(error = it)
                    }
                }
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Loader(text = "Pagination Loading")
                }
            }
            else -> {}
        }
    }
}
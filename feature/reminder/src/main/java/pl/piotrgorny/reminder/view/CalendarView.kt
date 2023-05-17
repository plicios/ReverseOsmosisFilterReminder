package pl.piotrgorny.reminder.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import pl.piotrgorny.reminder.viewModel.CalendarViewModel
import pl.piotrgorny.ui.paging.LazyPagingList


@Composable
fun  CalendarView() {
    val viewModel: CalendarViewModel = viewModel(factory = CalendarViewModel.Factory(
        LocalContext.current)
    )
    val events = viewModel.getReminders().collectAsLazyPagingItems()

    LazyPagingList(lazyPagingItems = events) {
        ReminderRow(filterReminder = it)
    }
}

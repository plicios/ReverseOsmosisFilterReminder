package pl.piotrgorny.reminder.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.data.ReminderRepository
import pl.piotrgorny.data.database.DatabaseReminderRepository
import pl.piotrgorny.model.FilterReminder

class CalendarViewModel(
    private val reminderRepository: ReminderRepository
): ViewModel() {
    fun getReminders(): Flow<PagingData<FilterReminder>> = reminderRepository.getPagedFlow().cachedIn(viewModelScope)

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CalendarViewModel(
                DatabaseReminderRepository(context)
            ) as T
        }
    }
}
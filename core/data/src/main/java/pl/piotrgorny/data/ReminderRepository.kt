package pl.piotrgorny.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.FilterReminder

interface ReminderRepository {
    fun getPagedFlow() : Flow<PagingData<FilterReminder>>

    suspend fun getForFilter(filterId: Long) : List<FilterReminder>

    suspend fun addReminder(reminder: FilterReminder)

    suspend fun addReminders(reminders: List<FilterReminder>)

    suspend fun setReminders(reminders: List<FilterReminder>)

    suspend fun removeRemindersByParentId(id: Long)
}
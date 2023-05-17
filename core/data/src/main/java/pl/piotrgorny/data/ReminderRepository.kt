package pl.piotrgorny.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.FilterReminder
import org.joda.time.LocalDate

interface ReminderRepository {
    fun getPagedFlow() : Flow<PagingData<FilterReminder>>

    suspend fun addReminder(reminder: FilterReminder)
}
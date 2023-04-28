package pl.piotrgorny.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDate

class CalendarViewModel(
): ViewModel() {
    private val pager = Pager(
        config = PagingConfig(1),
        pagingSourceFactory = {
            EventsPagingSource()
        }
    ).flow
    fun getEvents(): Flow<PagingData<LocalDate>> = pager.cachedIn(viewModelScope)
}
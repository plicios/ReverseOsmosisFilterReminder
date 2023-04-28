package pl.piotrgorny.reminder

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.joda.time.LocalDate

class EventsPagingSource : PagingSource<LocalDate, LocalDate>() {
    override fun getRefreshKey(state: PagingState<LocalDate, LocalDate>): LocalDate? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plusWeeks(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minusWeeks(1)
        }
    }

    override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, LocalDate> {
        return try {
            val startDate = params.key ?: LocalDate()
            val response = listOf(startDate.plusDays(3))

            LoadResult.Page(
                data = response,
                prevKey = startDate.minusWeeks(1),
                nextKey = startDate.plusWeeks(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
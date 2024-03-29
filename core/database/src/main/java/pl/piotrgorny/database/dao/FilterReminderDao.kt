package pl.piotrgorny.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.FilterReminder
import org.joda.time.LocalDate

@Dao
interface FilterReminderDao : ReverseOsmosisDao<FilterReminder> {
    @Query("SELECT * FROM ${FilterReminder.TABLE_NAME} ord")
    fun getPaged(): PagingSource<Int, FilterReminder>

    @Query("SELECT * FROM ${FilterReminder.TABLE_NAME} WHERE filterId = :filterId")
    fun getByFilter(filterId: Int) : Flow<List<FilterReminder>>

    @Delete
    suspend fun delete(entity: FilterReminder)
}
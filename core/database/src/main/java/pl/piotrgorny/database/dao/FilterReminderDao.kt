package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.FilterReminder

@Dao
interface FilterReminderDao : ReverseOsmosisDao<FilterReminder> {
    @Query("SELECT * FROM ${FilterReminder.TABLE_NAME} WHERE filterId = :filterId")
    fun getByFilter(filterId: Int) : Flow<List<FilterReminder>>
}
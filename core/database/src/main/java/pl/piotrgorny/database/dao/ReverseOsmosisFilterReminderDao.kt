package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.ReverseOsmosisFilterReminder

@Dao
interface ReverseOsmosisFilterReminderDao : ReverseOsmosisDao<ReverseOsmosisFilterReminder> {
    @Query("SELECT * FROM ${ReverseOsmosisFilterReminder.TABLE_NAME} WHERE filterId = :filterId")
    fun getByFilter(filterId: Int) : Flow<List<ReverseOsmosisFilterReminder>>
}
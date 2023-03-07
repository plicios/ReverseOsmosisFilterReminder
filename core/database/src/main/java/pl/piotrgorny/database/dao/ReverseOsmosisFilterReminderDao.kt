package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import pl.piotrgorny.database.entity.ReverseOsmosisFilterReminder

@Dao
interface ReverseOsmosisFilterReminderDao : ReverseOsmosisDao<ReverseOsmosisFilterReminder> {
    @Query("SELECT * FROM ${ReverseOsmosisFilterReminder.TABLE_NAME} WHERE filterId = :filterId")
    fun getByFilter(filterId: Int)
}
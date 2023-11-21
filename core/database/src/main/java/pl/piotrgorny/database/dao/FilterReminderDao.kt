package pl.piotrgorny.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import pl.piotrgorny.database.entity.Filter
import pl.piotrgorny.database.entity.FilterReminder

@Dao
interface FilterReminderDao : ReverseOsmosisDao<FilterReminder> {
    @Query("SELECT * FROM ${FilterReminder.TABLE_NAME}")
    fun getPaged(): PagingSource<Int, FilterReminder>

    @Query("SELECT * FROM ${FilterReminder.TABLE_NAME} WHERE ${FilterReminder.FILTER_ID_COLUMN} = :filterId")
    suspend fun getByFilter(filterId: Long) : List<FilterReminder>

    @Query("DELETE FROM ${FilterReminder.TABLE_NAME}")
    suspend fun clear()

    @Delete
    suspend fun delete(entity: FilterReminder)

    @Query("DELETE FROM ${FilterReminder.TABLE_NAME} WHERE ${FilterReminder.FILTER_ID_COLUMN} = :filterId")
    suspend fun deleteByFilter(filterId: Long)
}
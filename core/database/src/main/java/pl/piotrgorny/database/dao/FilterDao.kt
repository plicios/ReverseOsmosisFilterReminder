package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.Filter

@Dao
interface FilterDao : ReverseOsmosisDao<Filter> {
    @Query("SELECT * FROM ${Filter.TABLE_NAME}")
    fun getAll() : Flow<List<Filter>>

    @Query("SELECT * FROM ${Filter.TABLE_NAME} WHERE ${Filter.FILTER_SETUP_ID_COLUMN_NAME} = :filterSetupId")
    fun getByFilterSetup(filterSetupId: Int) : Flow<List<Filter>>

    @Query("SELECT * FROM ${Filter.TABLE_NAME} WHERE ${Filter.UID_COLUMN_NAME} = :uid")
    fun get(uid: Long) : Flow<Filter?>

    @Delete
    suspend fun delete(entity: Filter)

    @Query("DELETE FROM ${Filter.TABLE_NAME} WHERE ${Filter.UID_COLUMN_NAME} = :uid")
    suspend fun delete(uid: Long)

    @Query("DELETE FROM ${Filter.TABLE_NAME} WHERE ${Filter.FILTER_SETUP_ID_COLUMN_NAME} = :filterSetupId")
    suspend fun deleteByFilterSetup(filterSetupId: Long)
}
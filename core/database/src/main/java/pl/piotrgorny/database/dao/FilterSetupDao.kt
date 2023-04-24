package pl.piotrgorny.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.FilterSetup
import pl.piotrgorny.database.relation.FilterSetupWithFilters

@Dao
interface FilterSetupDao : ReverseOsmosisDao<FilterSetup> {
    @Query("SELECT * FROM ${FilterSetup.TABLE_NAME}")
    fun getAll() : Flow<List<FilterSetupWithFilters>>

    @Query("SELECT * FROM ${FilterSetup.TABLE_NAME} where uid = :uid")
    fun get(uid: Long) : Flow<FilterSetupWithFilters?>

    @Query("DELETE FROM ${FilterSetup.TABLE_NAME} WHERE uid = :uid")
    suspend fun delete(uid: Long)

    @Delete
    suspend fun delete(entity: FilterSetup)
}
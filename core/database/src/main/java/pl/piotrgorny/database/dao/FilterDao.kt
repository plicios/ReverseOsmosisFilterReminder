package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.Filter

@Dao
interface FilterDao : ReverseOsmosisDao<Filter> {
    @Query("SELECT * FROM ${Filter.TABLE_NAME} WHERE filterSetupId = :filterSetupId")
    fun getByFilterSetup(filterSetupId: Int) : Flow<List<Filter>>
}
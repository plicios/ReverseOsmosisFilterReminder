package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.ReverseOsmosisFilter

@Dao
interface ReverseOsmosisFilterDao : ReverseOsmosisDao<ReverseOsmosisFilter> {
    @Query("SELECT * FROM ${ReverseOsmosisFilter.TABLE_NAME} WHERE filterSetupId = :filterSetupId")
    fun getByFilterSetup(filterSetupId: Int) : Flow<List<ReverseOsmosisFilter>>
}
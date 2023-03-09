package pl.piotrgorny.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.database.entity.ReverseOsmosisFilterSetup

@Dao
interface ReverseOsmosisFilterSetupDao : ReverseOsmosisDao<ReverseOsmosisFilterSetup> {
    @Query("SELECT * FROM ${ReverseOsmosisFilterSetup.TABLE_NAME}")
    fun getAll() : Flow<List<ReverseOsmosisFilterSetup>>
}
package pl.piotrgorny.database.dao

import androidx.room.*
import pl.piotrgorny.database.entity.ReverseOsmosisFilterSetup

@Dao
interface ReverseOsmosisFilterSetupDao : ReverseOsmosisDao<ReverseOsmosisFilterSetup> {
    @Query("SELECT * FROM ${ReverseOsmosisFilterSetup.TABLE_NAME}")
    fun getAll(): List<ReverseOsmosisFilterSetup>
}
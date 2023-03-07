package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import pl.piotrgorny.database.entity.ReverseOsmosisFilter

@Dao
interface ReverseOsmosisFilterDao : ReverseOsmosisDao<ReverseOsmosisFilter> {
    @Query("SELECT * FROM ${ReverseOsmosisFilter.TABLE_NAME} WHERE setupId = :setupId")
    fun getByFilterSetup(setupId: Int)
}
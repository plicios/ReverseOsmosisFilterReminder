package pl.piotrgorny.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDate
import pl.piotrgorny.database.entity.Filter

@Dao
interface FilterDao : ReverseOsmosisDao<Filter> {
    @Query("SELECT * FROM ${Filter.TABLE_NAME} WHERE ${Filter.FILTER_SETUP_ID_COLUMN_NAME} = :filterSetupId")
    fun getByFilterSetup(filterSetupId: Int) : Flow<List<Filter>>

    @Query("SELECT * FROM ${Filter.TABLE_NAME} WHERE ${Filter.UID_COLUMN_NAME} = :uid")
    fun get(uid: Long) : Flow<Filter?>

    @Query("UPDATE ${Filter.TABLE_NAME} " +
            "SET ${Filter.TYPE_COLUMN_NAME} = :type, " +
            "${Filter.LIFESPAN_COLUMN_NAME} = :lifeSpan, " +
            "${Filter.INSTALLATION_DATE_COLUMN_NAME} = :installationDate " +
            "WHERE ${Filter.UID_COLUMN_NAME} = :uid")
    fun update(uid: Long, type: String, lifeSpan: String, installationDate: LocalDate)

    @Query("DELETE FROM ${Filter.TABLE_NAME} WHERE ${Filter.UID_COLUMN_NAME} = :uid")
    fun delete(uid: Long)
}
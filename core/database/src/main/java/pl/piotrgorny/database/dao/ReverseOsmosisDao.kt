package pl.piotrgorny.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Update

interface ReverseOsmosisDao<T> {
    @Insert(onConflict = ABORT)
    suspend fun insert(vararg entities: T) : List<Long>

    @Update
    suspend fun update(entity: T)
}
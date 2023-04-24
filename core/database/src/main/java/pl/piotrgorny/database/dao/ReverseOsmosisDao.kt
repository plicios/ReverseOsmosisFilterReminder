package pl.piotrgorny.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update

interface ReverseOsmosisDao<T> {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg entities: T) : List<Long>

    @Update
    suspend fun update(entity: T)
}
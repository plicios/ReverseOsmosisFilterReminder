package pl.piotrgorny.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface ReverseOsmosisDao<T> {
    @Insert
    suspend fun insert(vararg setups: T)

    @Update
    suspend fun updateSetup(setup: T)

    @Delete
    suspend fun deleteSetup(setup: T)
}
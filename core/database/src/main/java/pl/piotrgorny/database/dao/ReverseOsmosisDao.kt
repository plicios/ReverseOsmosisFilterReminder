package pl.piotrgorny.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface ReverseOsmosisDao<T> {
    @Insert
    fun insert(vararg setups: T)

    @Update
    fun updateSetup(setup: T)

    @Delete
    fun deleteSetup(setup: T)
}
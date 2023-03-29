package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FilterSetup.TABLE_NAME)
data class FilterSetup(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "setup"
    }
}

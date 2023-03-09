package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReverseOsmosisFilterSetup.TABLE_NAME)
data class ReverseOsmosisFilterSetup(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    companion object {
        const val TABLE_NAME = "setup"
    }
}

package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReverseOsmosisFilterSetup.TABLE_NAME)
data class ReverseOsmosisFilterSetup(
    @PrimaryKey val uid: Int,
    val name: String
) {
    companion object {
        const val TABLE_NAME = "setup"
    }
}

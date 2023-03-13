package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = ReverseOsmosisFilter.TABLE_NAME)
data class ReverseOsmosisFilter(
    val filterSetupId: Long, //ForeignKey
    val type: String,
    val name: String,
    val lifeSpan: Int, //TODO Maybe change to some timeSpan type
    val installationDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "filter"
    }
}

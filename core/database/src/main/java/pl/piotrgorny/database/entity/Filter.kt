package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = Filter.TABLE_NAME)
data class Filter(
    val filterSetupId: Long, //ForeignKey
    val type: String,
    val lifeSpan: String,
    val installationDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "filter"
    }
}

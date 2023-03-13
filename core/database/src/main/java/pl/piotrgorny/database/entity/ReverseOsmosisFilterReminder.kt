package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = ReverseOsmosisFilterReminder.TABLE_NAME)
data class ReverseOsmosisFilterReminder(
    val filterId: Long, //ForeignKey
    val alarmDate: Date,
    val type: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "filter_reminder"
    }
}

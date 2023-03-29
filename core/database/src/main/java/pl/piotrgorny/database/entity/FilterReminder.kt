package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = FilterReminder.TABLE_NAME)
data class FilterReminder(
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

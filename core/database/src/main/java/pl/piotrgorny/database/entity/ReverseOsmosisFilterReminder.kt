package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = ReverseOsmosisFilterReminder.TABLE_NAME)
data class ReverseOsmosisFilterReminder(
    @PrimaryKey val uid: Int,
    val filterId: Int, //ForeignKey
    val alarmDate: Date
) {
    companion object {
        const val TABLE_NAME = "filter_reminder"
    }
}

package pl.piotrgorny.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = FilterReminder.TABLE_NAME)
data class FilterReminder(
    @ColumnInfo(FILTER_ID_COLUMN)
    val filterId: Long, //ForeignKey
    @ColumnInfo(ALARM_DATE_COLUMN)
    val alarmDate: DateTime,
    @ColumnInfo(TYPE_COLUMN)
    val type: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "filter_reminder"
        const val FILTER_ID_COLUMN = "filter_id"
        const val ALARM_DATE_COLUMN = "alarm_date"
        const val TYPE_COLUMN = "type"
    }
}

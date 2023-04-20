package pl.piotrgorny.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDate

@Entity(tableName = Filter.TABLE_NAME)
data class Filter(
    @ColumnInfo(FILTER_SETUP_ID_COLUMN_NAME)
    val filterSetupId: Long, //ForeignKey
    @ColumnInfo(TYPE_COLUMN_NAME)
    val type: String,
    @ColumnInfo(LIFESPAN_COLUMN_NAME)
    val lifeSpan: String,
    @ColumnInfo(INSTALLATION_DATE_COLUMN_NAME)
    val installationDate: LocalDate
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(UID_COLUMN_NAME)
    var uid: Long = 0
    companion object {
        const val TABLE_NAME = "filter"
        const val UID_COLUMN_NAME = "uid"
        const val FILTER_SETUP_ID_COLUMN_NAME = "filterSetupId"
        const val TYPE_COLUMN_NAME = "type"
        const val LIFESPAN_COLUMN_NAME = "lifeSpan"
        const val INSTALLATION_DATE_COLUMN_NAME = "installationDate"
    }
}

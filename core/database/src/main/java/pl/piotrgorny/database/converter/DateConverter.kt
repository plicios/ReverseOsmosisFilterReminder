package pl.piotrgorny.database.converter

import androidx.room.TypeConverter
import org.joda.time.LocalDate


class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? = value?.let { LocalDate(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? = date?.toDate()?.time
}
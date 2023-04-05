package pl.piotrgorny.database.converter

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.LocalDate


class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? = value?.let { DateTime(it) }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): Long? = date?.millis
}
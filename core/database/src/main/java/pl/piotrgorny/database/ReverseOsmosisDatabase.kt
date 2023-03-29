package pl.piotrgorny.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.piotrgorny.database.converter.DateConverter
import pl.piotrgorny.database.dao.FilterSetupDao
import pl.piotrgorny.database.dao.FilterDao
import pl.piotrgorny.database.dao.FilterReminderDao
import pl.piotrgorny.database.entity.Filter
import pl.piotrgorny.database.entity.FilterReminder
import pl.piotrgorny.database.entity.FilterSetup

@Database(entities = [FilterSetup::class, Filter::class, FilterReminder::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ReverseOsmosisDatabase : RoomDatabase() {
    abstract fun filterSetupDao(): FilterSetupDao
    abstract fun filterDao(): FilterDao
    abstract fun filterReminderDao(): FilterReminderDao
}
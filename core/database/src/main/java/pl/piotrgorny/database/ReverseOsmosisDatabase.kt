package pl.piotrgorny.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.piotrgorny.database.dao.ReverseOsmosisFilterSetupDao
import pl.piotrgorny.database.dao.ReverseOsmosisFilterDao
import pl.piotrgorny.database.dao.ReverseOsmosisFilterReminderDao
import pl.piotrgorny.database.entity.ReverseOsmosisFilter
import pl.piotrgorny.database.entity.ReverseOsmosisFilterReminder
import pl.piotrgorny.database.entity.ReverseOsmosisFilterSetup

@Database(entities = [ReverseOsmosisFilterSetup::class, ReverseOsmosisFilter::class, ReverseOsmosisFilterReminder::class], version = 1)
abstract class ReverseOsmosisDatabase : RoomDatabase() {
    abstract fun reverseOsmosisFilterSetupDao(): ReverseOsmosisFilterSetupDao
    abstract fun reverseOsmosisFilterDao(): ReverseOsmosisFilterDao
    abstract fun reverseOsmosisFilterReminderDao(): ReverseOsmosisFilterReminderDao
}
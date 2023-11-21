package pl.piotrgorny.data.database

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.Room
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.piotrgorny.data.ReminderRepository
import pl.piotrgorny.database.ReverseOsmosisDatabase
import pl.piotrgorny.model.FilterReminder
import pl.piotrgorny.database.entity.FilterReminder as FilterReminderEntity

class DatabaseReminderRepository(context: Context) : ReminderRepository {
    private val database: ReverseOsmosisDatabase = Room.databaseBuilder(context, ReverseOsmosisDatabase::class.java, "reverse-osmosis-db").fallbackToDestructiveMigration().build()
    private val filterReminderDao by lazy {
        database.filterReminderDao()
    }

    override fun getPagedFlow(): Flow<PagingData<FilterReminder>>  = Pager(
            config = PagingConfig(5),
            pagingSourceFactory = {
                filterReminderDao.getPaged()
            }
        ).flow.map {
            filterReminderPagingData -> filterReminderPagingData.map {
                filterReminderEntity -> filterReminderEntity.toModel()
            }
        }

    override suspend fun getForFilter(filterId: Long): List<FilterReminder> {
        return filterReminderDao.getByFilter(filterId).map { it.toModel() }
    }

    override suspend fun addReminder(reminder: FilterReminder) {
        filterReminderDao.insert(reminder.toEntity())
    }

    override suspend fun addReminders(reminders: List<FilterReminder>) {
        filterReminderDao.insert(*reminders.map { reminder -> reminder.toEntity() }.toTypedArray())
    }

    override suspend fun setReminders(reminders: List<FilterReminder>) {
        database.withTransaction {
            filterReminderDao.clear()
            addReminders(reminders)
        }
    }

    override suspend fun removeRemindersByParentId(id: Long) {
        filterReminderDao.deleteByFilter(id)
    }
}

fun FilterReminderEntity.toModel() = FilterReminder(
    filterId,
    alarmDate,
    FilterReminder.Type.valueOf(type)
)

fun FilterReminder.toEntity() = FilterReminderEntity(
    filterId,
    alarmDate,
    type.name
)
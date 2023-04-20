package pl.piotrgorny.data.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.piotrgorny.data.FilterRepository
import pl.piotrgorny.database.ReverseOsmosisDatabase
import pl.piotrgorny.model.Filter

class DatabaseFilterRepository(context: Context) : FilterRepository {

    override fun getFilter(filterId: Long): Flow<Filter?> {
        return database.filterDao().get(filterId).map { it?.toModel() }
    }

    override suspend fun addFilter(filter: Filter) {
        database.filterDao().insert(filter.toEntity(-1))
    }

    override suspend fun modifyFilter(filter: Filter) {
        database.filterDao().update(
            filter.id,
            filter.type.name,
            filter.lifeSpan.name,
            filter.installationDate
        )
    }

    override suspend fun removeFilter(filterId: Long) {
        database.filterDao().delete(filterId)
    }

    private val database: ReverseOsmosisDatabase = Room.databaseBuilder(context, ReverseOsmosisDatabase::class.java, "reverse-osmosis-db").build()


    companion object {
        private var _instance: DatabaseFilterRepository? = null
        fun instance(context: Context) : DatabaseFilterRepository {
            _instance?.let {
                return it
            }
            return DatabaseFilterRepository(context).also {
                _instance = it
            }
        }
    }
}

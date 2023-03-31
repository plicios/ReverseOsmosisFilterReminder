package pl.piotrgorny.data.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.database.ReverseOsmosisDatabase
import pl.piotrgorny.database.entity.Filter
import pl.piotrgorny.database.relation.FilterSetupWithFilters
import pl.piotrgorny.model.FilterSetup

class DatabaseFilterSetupRepository(context: Context) : FilterSetupRepository {

    private val database: ReverseOsmosisDatabase = Room.databaseBuilder(context, ReverseOsmosisDatabase::class.java, "reverse-osmosis-db").build()
    override fun getFilterSetups(): Flow<List<FilterSetup>> {
        return database.filterSetupDao().getAll().map { list ->
            list.map { it.toModel() }
        }
    }

    override fun getFilterSetup(id: Long): Flow<FilterSetup?> {
        return database.filterSetupDao().get(id).map {
            it?.toModel()
        }
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        val id = database.filterSetupDao().insert(filterSetup.toEntity()).first()
        database.filterDao().insert(*filterSetup.filters.map { it.toEntity(id) }.toTypedArray())
    }

    companion object {
        private var _instance: DatabaseFilterSetupRepository? = null
        fun instance(context: Context) : DatabaseFilterSetupRepository {
            _instance?.let {
                return it
            }
            return DatabaseFilterSetupRepository(context).also {
                _instance = it
            }
        }
    }
}

fun FilterSetupWithFilters.toModel() = FilterSetup(
    filterSetup.uid,
    filterSetup.name,
    FilterSetup.Type.valueOf(filterSetup.type),
    filters.map { it.toModel() }
)

fun Filter.toModel() = pl.piotrgorny.model.Filter(
    pl.piotrgorny.model.Filter.Type.valueOf(type),
    installationDate,
    pl.piotrgorny.model.Filter.LifeSpan.valueOf(lifeSpan)
)

fun FilterSetup.toEntity() = pl.piotrgorny.database.entity.FilterSetup(
    name,
    type.name
)

fun pl.piotrgorny.model.Filter.toEntity(setupId: Long) = Filter(
    setupId,
    type.name,
    lifeSpan.name,
    installationDate
)
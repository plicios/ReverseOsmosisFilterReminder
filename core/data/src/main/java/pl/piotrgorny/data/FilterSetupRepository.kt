package pl.piotrgorny.data

import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup

interface FilterSetupRepository {
    fun getFilterSetups(): Flow<List<FilterSetup>>
    fun getFilterSetup(id: Long): Flow<FilterSetup?>
    fun getFilters(): Flow<List<Filter>>
    suspend fun addFilterSetup(filterSetup: FilterSetup)
    suspend fun updateFilterSetup(filterSetup: FilterSetup)
    suspend fun deleteFilterSetup(filterSetupId: Long)
}
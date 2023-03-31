package pl.piotrgorny.data

import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.FilterSetup

interface FilterSetupRepository {
    fun getFilterSetups(): Flow<List<FilterSetup>>
    fun getFilterSetup(id: Long): Flow<FilterSetup?>
    suspend fun addFilterSetup(filterSetup: FilterSetup)
}
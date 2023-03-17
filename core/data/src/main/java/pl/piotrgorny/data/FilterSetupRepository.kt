package pl.piotrgorny.data

import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.FilterSetup

interface FilterSetupRepository {
    fun getFilterSetups(): Flow<List<FilterSetup>>
    suspend fun addFilterSetup(filterSetup: FilterSetup)
}
package pl.piotrgorny.data.database

import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.FilterSetup

class DatabaseFilterSetupRepository : FilterSetupRepository {
    override fun getFilterSetups(): Flow<List<FilterSetup>> {
        TODO("add proper database implementation")
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        TODO("add proper database implementation")
    }
}
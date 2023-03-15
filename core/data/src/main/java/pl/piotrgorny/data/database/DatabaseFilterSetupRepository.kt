package pl.piotrgorny.data.database

import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.FilterSetup

class DatabaseFilterSetupRepository : FilterSetupRepository {
    override suspend fun getFilterSetups(): List<FilterSetup> {
        TODO("add proper database implementation")
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        TODO("add proper database implementation")
    }
}
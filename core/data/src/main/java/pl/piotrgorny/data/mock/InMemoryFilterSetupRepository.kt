package pl.piotrgorny.data.mock

import kotlinx.coroutines.delay
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.FilterSetup

class InMemoryFilterSetupRepository : FilterSetupRepository {

    private val inMemoryList = mutableListOf(FilterSetup("Mock item"))

    override suspend fun getFilterSetups(): List<FilterSetup> {
//        delay(5000L)
//        return emptyList()
        return inMemoryList.toList()
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        inMemoryList.add(filterSetup)
    }
}
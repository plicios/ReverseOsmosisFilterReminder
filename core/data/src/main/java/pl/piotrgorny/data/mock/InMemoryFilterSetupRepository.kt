package pl.piotrgorny.data.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.FilterSetup

object InMemoryFilterSetupRepository : FilterSetupRepository {

    private val inMemoryList = MutableStateFlow(emptyList<FilterSetup>())

    override fun getFilterSetups(): Flow<List<FilterSetup>> {
        return inMemoryList.asStateFlow()
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        inMemoryList.value += filterSetup
    }
}
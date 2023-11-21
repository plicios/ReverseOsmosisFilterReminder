package pl.piotrgorny.data.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import pl.piotrgorny.common.replace
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup

object InMemoryFilterSetupRepository : FilterSetupRepository {

    private val inMemoryList = MutableStateFlow(emptyList<FilterSetup>())

    override fun getFilterSetups(): Flow<List<FilterSetup>> {
        return inMemoryList.asStateFlow()
    }

    override fun getFilterSetup(id: Long): Flow<FilterSetup?> {
        return inMemoryList.asStateFlow().map { it.firstOrNull{ filterSetup ->  filterSetup.id == id } }
    }

    override fun getFilters(): Flow<List<Filter>> {
        return inMemoryList.asStateFlow().map { it.flatMap { it.filters } }
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        inMemoryList.value += filterSetup.copy(id = inMemoryList.value.size.toLong())
    }

    override suspend fun updateFilterSetup(filterSetup: FilterSetup) {
        inMemoryList.value = inMemoryList.value.replace(filterSetup) {
            it.id == filterSetup.id
        }
    }

    override suspend fun deleteFilterSetup(filterSetupId: Long) {
        inMemoryList.value = inMemoryList.value.toMutableList().apply {
            removeIf { it.id == filterSetupId }
        }
    }
}
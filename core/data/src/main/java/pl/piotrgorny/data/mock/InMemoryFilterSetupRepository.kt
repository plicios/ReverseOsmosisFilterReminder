package pl.piotrgorny.data.mock

import kotlinx.coroutines.flow.*
import pl.piotrgorny.data.FilterRepository
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup

object InMemoryFilterSetupRepository : FilterSetupRepository, FilterRepository {

    private val inMemoryList = MutableStateFlow(emptyList<FilterSetup>())

    override fun getFilterSetups(): Flow<List<FilterSetup>> {
        return inMemoryList.asStateFlow()
    }

    override fun getFilterSetup(id: Long): Flow<FilterSetup?> {
        return inMemoryList.asStateFlow().map { it.firstOrNull{it.id == id} }
    }

    override suspend fun addFilterSetup(filterSetup: FilterSetup) {
        inMemoryList.value += filterSetup
    }

    override fun getFilter(filterId: Long): Flow<Filter?> {
        return inMemoryList.asStateFlow().map {
            it.map { filterSetup -> filterSetup.filters }
                .flatten()
                .firstOrNull{ filter -> filter.id == filterId}
        }
    }

    override suspend fun addFilter(filter: Filter) {
        //Adding filters is not supported
    }

    override suspend fun modifyFilter(filter: Filter) {
        //Modifing filters is not supported
    }

    override suspend fun removeFilter(filterId: Long) {
        //Removing filters is not supported
    }
}
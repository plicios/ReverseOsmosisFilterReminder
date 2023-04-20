package pl.piotrgorny.data

import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.model.Filter

interface FilterRepository {
    fun getFilter(filterId: Long): Flow<Filter?>
    suspend fun addFilter(filter: Filter)
    suspend fun modifyFilter(filter: Filter)
    suspend fun removeFilter(filterId: Long)
}
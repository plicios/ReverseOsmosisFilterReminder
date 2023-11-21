package pl.piotrgorny.data.database

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.piotrgorny.data.Event
import pl.piotrgorny.data.EventsRepository
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.diff.FilterListDiff

class DatabaseFilterEventsRepository(private val filterSetupRepository: FilterSetupRepository) : EventsRepository {

    var lastFilters: List<Filter>? = null

    private val newFilter: Channel<Event> = Channel()
    private val modifiedFilter: Channel<Event> = Channel()
    private val removedFilter: Channel<Long> = Channel()

    suspend fun observeEvents() {
        filterSetupRepository.getFilters()
            .collect {
                lastFilters?.let {filters ->
                    FilterListDiff({ filter ->
                        filter.toEvent()?.let { event -> newFilter.send(event) }
                    },{filter ->
                        filter.toEvent()?.let { event -> modifiedFilter.send(event) }
                    }, {
                        removedFilter.send(it)
                    }
                    ).compare(it, filters)
                }
                lastFilters = it
            }
    }

    override fun getNewEvent(): Flow<Event> = newFilter.receiveAsFlow()

    override fun getModifiedEvent(): Flow<Event> = modifiedFilter.receiveAsFlow()

    override fun getRemovedEvent(): Flow<Long> = removedFilter.receiveAsFlow()

    private fun Filter.toEvent() : Event? = id?.let {
        Event(
            it, toString(), expirationDate
        )
    }
}
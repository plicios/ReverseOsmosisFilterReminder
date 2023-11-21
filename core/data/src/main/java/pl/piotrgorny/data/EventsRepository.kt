package pl.piotrgorny.data

import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDate

interface EventsRepository {
    fun getNewEvent() : Flow<Event>
    fun getModifiedEvent() : Flow<Event>
    fun getRemovedEvent() : Flow<Long>
}

data class Event(val id: Long, val payload: String, val date: LocalDate)
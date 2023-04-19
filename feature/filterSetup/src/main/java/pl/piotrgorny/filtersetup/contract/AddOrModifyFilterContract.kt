package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import org.joda.time.LocalDate

class AddOrModifyFilterContract {
    sealed class Event : ViewEvent {
        data class TypeChange(val type: Filter.Type) : Event()
        data class LifeSpanChange(val lifeSpan: Filter.LifeSpan) : Event()
        data class InstallationDateChange(val installationDate: LocalDate) : Event()
        object AddOrModifyFilter: Event()
        object RemoveFilter: Event()
    }
    data class State(
        val type: Filter.Type? = null,
        val lifeSpan: Filter.LifeSpan? = null,
        val installationDate: LocalDate? = null,
    ) : ViewState {
        constructor(filter: Filter) :
                this(filter.type,
                    filter.lifeSpan,
                    filter.installationDate
                )
    }

    sealed class Effect : ViewSideEffect {
        data class FilterAddedOrModified(val filter: Filter) : Effect()
        object FilterRemoved : Effect()
    }
}
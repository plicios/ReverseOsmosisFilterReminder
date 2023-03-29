package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import java.util.Date

class ModifyFilterContract {
    sealed class Event : ViewEvent {
        data class TypeChange(val type: Filter.Type) : Event()
        data class LifeSpanChange(val lifeSpan: Filter.LifeSpan) : Event()
        data class InstallationDateChange(val installationDate: Date) : Event()
        object ModifyFilter: Event()
        object RemoveFilter: Event()
    }
    data class State(
        val modifiedFilter: Filter,
        val type: Filter.Type,
        val lifeSpan: Filter.LifeSpan,
        val installationDate: Date
    ) : ViewState {
        constructor(modifiedFilter: Filter) :
                this(modifiedFilter,
                    modifiedFilter.type,
                    modifiedFilter.lifeSpan,
                    modifiedFilter.installationDate)
    }

    sealed class Effect : ViewSideEffect {
        data class FilterModified(val newFilter: Filter, val oldFilter: Filter) : Effect()
        data class FilterRemoved(val filter: Filter) : Effect()
    }
}
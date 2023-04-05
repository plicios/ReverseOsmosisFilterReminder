package pl.piotrgorny.filtersetup.contract

import org.joda.time.LocalDate
import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import java.util.Date

class AddFilterContract {
    sealed class Event : ViewEvent {
        data class TypeChange(val type: Filter.Type) : Event()
        data class LifeSpanChange(val lifeSpan: Filter.LifeSpan) : Event()
        data class InstallationDateChange(val installationDate: LocalDate) : Event()
        object AddFilter: Event()
    }
    data class State(
        val type: Filter.Type? = null,
        val lifeSpan: Filter.LifeSpan? = null,
        val installationDate: LocalDate = LocalDate()
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class FilterAdded(val filter: Filter) : Effect()
    }
}
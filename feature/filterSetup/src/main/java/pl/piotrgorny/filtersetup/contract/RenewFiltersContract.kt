package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import pl.piotrgorny.model.FilterSetup

class RenewFiltersContract {
    sealed class Event : ViewEvent {
        data class FilterSelected(val filter: Filter) : Event()
        data class FilterDeselected(val filter: Filter) : Event()
        object RenewFilters: Event()
    }
    data class State(
        val filterSetup: FilterSetup? = null,
        val filterSelection: List<Pair<Filter, Boolean>> = emptyList(),
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object FiltersRenewed : Effect()
    }
}
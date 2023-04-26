package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import org.joda.time.LocalDate
import pl.piotrgorny.model.Filter

class RenewFiltersContract {
    sealed class Event : ViewEvent {
        data class RenewalDateChanged(val renewalDate: LocalDate) : Event()
        data class FilterSelectionChange(val filter: Filter, val selected: Boolean) : Event()
        object RenewFilters : Event()
    }

    data class State(
        val filterSetup: FilterSetup? = null,
        val renewalDate: LocalDate = LocalDate(),
        val filterSelection: Map<Filter, Boolean> = mapOf(),
        val isLoading: Boolean = false) : ViewState
    {
        constructor(filterSetup: FilterSetup) : this(filterSetup = filterSetup, filterSelection = filterSetup.filters.associateWith {
            it.getExpirationDate().isBefore(LocalDate())
        }, renewalDate = filterSetup.filters.firstOrNull()?.installationDate ?: LocalDate())
    }

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()
        sealed class Navigation : Effect() {
            object BackToFilterSetups : Navigation()
        }
    }
}
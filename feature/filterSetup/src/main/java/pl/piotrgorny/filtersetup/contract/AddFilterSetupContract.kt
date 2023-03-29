package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState

class AddFilterSetupContract {
    sealed class Event : ViewEvent {
        object AddFilterSetup : Event()
        data class NameChange(val name: String) : Event()
        object RequestAddFilter : Event()
        object DismissAddFilter : Event()
        data class RequestModifyFilter(val filter: Filter) : Event()
        object DismissModifyFilter : Event()
        data class AddFilter(val filter: Filter) : Event()
        data class ModifyFilter(val oldFilter: Filter, val newFilter: Filter) : Event()
        data class RemoveFilter(val filter: Filter) : Event()
    }

    data class State(
        val name: String = "",
        val filters: List<Filter> = emptyList(),
        val isAddingFilter: Boolean = false,
        val filterBeingModified: Filter? = null,
        val isLoading: Boolean = false) : ViewState

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()
        sealed class Navigation : Effect() {
            object BackToFilterSetups : Navigation()
        }
    }
}

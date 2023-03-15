package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState

class FilterSetupsContract {
    sealed class Event : ViewEvent {
        data class FilterSetupSelection(val filterSetup: FilterSetup) : Event()
        object AddFilterSetup : Event()
    }

    data class State(val filterSetups: List<FilterSetup> = listOf(), val isLoading: Boolean = false) : ViewState

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()
        sealed class Navigation : Effect() {
            data class ToFilterSetupDetails(val filterSetup: FilterSetup) : Navigation()
            object ToAddFilterSetup : Navigation()
        }
    }
}
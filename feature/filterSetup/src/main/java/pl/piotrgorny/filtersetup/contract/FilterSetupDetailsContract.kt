package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState

class FilterSetupDetailsContract {
    sealed class Event : ViewEvent

    data class State(val filterSetup: FilterSetup? = null, val isLoading: Boolean = false) : ViewState

    sealed class Effect : ViewSideEffect
}
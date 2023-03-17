package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState
import java.util.*

class AddFilterSetupContract {
    sealed class Event : ViewEvent {
        object AddFilterSetup : Event()
        data class NameChange(val name: String) : Event()
        object RequestAddFilter : Event()
        object DismissAddFilter : Event()
        data class AddFilter(val filter: Filter) : Event()
    }

    data class State(
        val name: String = "",
        val filters: List<Filter> = listOf(
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic),
            Filter("test", 1, Date(), Filter.Type.BioCeramic)
        ),
        val isAddFilterDialogOpen: Boolean = false,
        val isLoading: Boolean = false) : ViewState

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()
        sealed class Navigation : Effect() {
            object BackToFilterSetups : Navigation()
        }
    }
}
package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState

class AddOrModifyFilterSetupContract {
    sealed class Event : ViewEvent {
        object AddOrModifyFilterSetup : Event()
        object RequestModifyFilterSetup: Event()

        data class NameChange(val name: String) : Event()
        data class TypeChange(val type: FilterSetup.Type) : Event()

        object RequestAddFilter : Event()
        data class RequestModifyFilter(val filter: Filter) : Event()
        data class RequestRemoveFilter(val filter: Filter) : Event()

        data class AddFilter(val filter: Filter) : Event()
        data class ModifyFilter(val index: Int, val newFilter: Filter) : Event()
        data class RemoveFilter(val index: Int) : Event()
    }

    data class State(
        val name: String = "",
        val type: FilterSetup.Type = FilterSetup.Type.Custom,
        val filters: List<Filter> = emptyList(),
        val isLoading: Boolean = false,
        val stateType: Type = Type.Add
    ) : ViewState {
        constructor(filterSetup: FilterSetup) :
                this(filterSetup.name,
                    filterSetup.type,
                    filterSetup.filters,
                    stateType = Type.View
                )
        sealed class Type {
            object Add: Type()
            object Modify: Type()
            object View: Type()
        }
    }

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()
        sealed class Navigation : Effect() {
            object BackToFilterSetups : Navigation()
            data class OpenAddOrModifyFilterDialog(val index: Int? = null, val filter: Filter? = null) : Navigation()
            data class OpenRemoveFilterDialog(val index: Int) : Navigation()
        }
    }
}

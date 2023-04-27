package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
import pl.piotrgorny.mvi.ViewState

class AddOrModifyFilterSetupContract {
    sealed class Event : ViewEvent {
        object AddOrModifyFilterSetup : Event()
        object RemoveFilterSetup : Event()
        object RequestModifyFilterSetup: Event()
        object RequestRemoveFilterSetup: Event()

        data class NameChange(val name: String) : Event()
        data class TypeChange(val type: FilterSetup.Type) : Event()

        object RequestAddFilter : Event()
        data class RequestModifyFilter(val index: Int, val filter: Filter) : Event()
        data class RequestRemoveFilter(val index: Int) : Event()
//        object RequestRenewFilters : Event()

        data class AddFilter(val filter: Filter) : Event()
        data class ModifyFilter(val index: Int, val newFilter: Filter) : Event()
        data class RemoveFilter(val index: Int) : Event()
        object RenewFilters : Event()
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
        enum class Type(val isEditable: Boolean, val canSwitchToEdit: Boolean, val canDelete: Boolean, val canSwitchToRenew: Boolean) {
            Add(true, false, false, false),
            Modify(true, false, true, true),
            View(false, true, true, true);
//            RenewFilters(false, true, true, false);

            fun canAny() : Boolean = canSwitchToEdit || canDelete || canSwitchToRenew
        }
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object BackToFilterSetups : Navigation()
            data class OpenAddOrModifyFilterDialog(val index: Int? = null, val filter: Filter? = null) : Navigation()
            data class OpenRemoveFilterDialog(val index: Int) : Navigation()
            data class OpenRemoveFilterSetupDialog(val filterSetupId: Long) : Navigation()
            data class OpenRenewFiltersDialog(val filterSetupId: Long) : Navigation()
        }
    }
}

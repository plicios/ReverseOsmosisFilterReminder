package pl.piotrgorny.filtersetup.contract

import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.ViewEvent
import pl.piotrgorny.mvi.ViewSideEffect
//import pl.piotrgorny.mvi.ViewState
import org.joda.time.LocalDate
import pl.piotrgorny.mvi.ValidationViewState
//import pl.piotrgorny.ui.Error

class AddOrModifyFilterContract {
    sealed class Event : ViewEvent {
        data class TypeChange(val type: Filter.Type) : Event()
        data class LifeSpanChange(val lifeSpan: Filter.LifeSpan) : Event()
        data class InstallationDateChange(val installationDate: LocalDate) : Event()
        object AddOrModifyFilter: Event()
        object RemoveFilter: Event()
    }
    data class State(
        val type: Filter.Type? = null,
        val lifeSpan: Filter.LifeSpan? = null,
        val installationDate: LocalDate? = null,
//        val typeError: Error? = null,
//        val lifeSpanError: Error? = null,
//        val installationDateError: Error? = null,
        val stateType: Type,
        override val checkValidity: Boolean = false
    ) : ValidationViewState() {
        constructor(type: Filter.Type? = null,
                    lifeSpan: Filter.LifeSpan? = null,
                    installationDate: LocalDate? = null) :
                this(type = type,
                    lifeSpan = lifeSpan,
                    installationDate = installationDate,
                    stateType = if(type != null && lifeSpan != null && installationDate != null) Type.Edit else Type.Add
                )
        enum class Type{
            Add,
            Edit
        }
    }

    sealed class Effect : ViewSideEffect {
        data class FilterAddedOrModified(val filter: Filter) : Effect()
        object FilterRemoved : Effect()
    }
}
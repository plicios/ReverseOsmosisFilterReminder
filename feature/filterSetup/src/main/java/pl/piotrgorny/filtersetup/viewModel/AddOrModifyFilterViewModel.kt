package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.joda.time.LocalDate
import pl.piotrgorny.common.tomorrow
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterContract
import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.MviBaseViewModel
import pl.piotrgorny.mvi.Error

class AddOrModifyFilterViewModel(
    type: Filter.Type?,
    installationDate: LocalDate?,
    lifeSpan: Filter.LifeSpan?
    ) : MviBaseViewModel<
        AddOrModifyFilterContract.Event,
        AddOrModifyFilterContract.State,
        AddOrModifyFilterContract.Effect
>(AddOrModifyFilterContract.State(type, lifeSpan, installationDate)) {

    override fun handleEvents(event: AddOrModifyFilterContract.Event) {
        when(event) {
            is AddOrModifyFilterContract.Event.TypeChange -> {
                setState { copy(type = event.type) }
                if(viewState.value.checkValidity)
                    validateInput()
            }
            is AddOrModifyFilterContract.Event.LifeSpanChange -> {
                setState { copy(lifeSpan = event.lifeSpan) }
                if(viewState.value.checkValidity)
                    validateInput()
            }
            is AddOrModifyFilterContract.Event.InstallationDateChange -> {
                setState { copy(installationDate = event.installationDate) }
                if(viewState.value.checkValidity)
                    validateInput()
            }
            is AddOrModifyFilterContract.Event.AddOrModifyFilter -> {
                if (validateInput()) {
                    with(viewState.value){
                        setEffect { AddOrModifyFilterContract.Effect.FilterAddedOrModified(
                            Filter(
                                type!!,
                                installationDate!!,
                                lifeSpan!!
                            )
                        ) }
                    }

                }
            }
            is AddOrModifyFilterContract.Event.RemoveFilter -> {
                setEffect { AddOrModifyFilterContract.Effect.FilterRemoved }
            }
        }
    }

    private fun validateInput() = with(viewState.value) {
        var valid = true
        setState { copy(checkValidity = true).also { clearErrors() } }
        if (type == null) {
            setState { copy().also { setError("typeError", Error.NoValue) } }
            valid = false
        }
        if (lifeSpan == null) {
            setState { copy().also { setError("lifeSpanError", Error.NoValue) } }
            valid = false
        }
        if (installationDate == null) {
            setState { copy().also { setError("installationDateError", Error.NoValue) } }
            valid = false
        } else if (installationDate.isAfter(tomorrow())) {
            setState { copy().also { setError("installationDateError", Error.IncorrectValue) } }
            valid = false
        }
        valid
    }

    class Factory(
        private val type: Filter.Type?,
        private val installationDate: LocalDate?,
        private val lifeSpan: Filter.LifeSpan?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddOrModifyFilterViewModel(
                type, installationDate, lifeSpan
            ) as T
        }
    }
}
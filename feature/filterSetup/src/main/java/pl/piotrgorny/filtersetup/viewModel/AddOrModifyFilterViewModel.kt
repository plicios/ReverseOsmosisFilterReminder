package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterContract
import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.MviBaseViewModel

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
            }
            is AddOrModifyFilterContract.Event.LifeSpanChange -> {
                setState { copy(lifeSpan = event.lifeSpan) }
            }
            is AddOrModifyFilterContract.Event.InstallationDateChange -> {
                setState { copy(installationDate = event.installationDate) }
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

                } else {
                    //TODO add error handling
                }
            }
            is AddOrModifyFilterContract.Event.RemoveFilter -> {
                setEffect { AddOrModifyFilterContract.Effect.FilterRemoved }
            }
        }
    }

    private fun validateInput() = true

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
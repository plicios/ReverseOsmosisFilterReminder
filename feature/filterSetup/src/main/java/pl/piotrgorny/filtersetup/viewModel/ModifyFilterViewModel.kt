package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.piotrgorny.filtersetup.contract.ModifyFilterContract
import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.MviBaseViewModel

class ModifyFilterViewModel(
    modifiedFilter: Filter
    ) : MviBaseViewModel<
        ModifyFilterContract.Event,
        ModifyFilterContract.State,
        ModifyFilterContract.Effect
>(ModifyFilterContract.State(modifiedFilter)) {

    override fun handleEvents(event: ModifyFilterContract.Event) {
        when(event) {
            is ModifyFilterContract.Event.TypeChange -> {
                setState { copy(type = event.type) }
            }
            is ModifyFilterContract.Event.LifeSpanChange -> {
                setState { copy(lifeSpan = event.lifeSpan) }
            }
            is ModifyFilterContract.Event.InstallationDateChange -> {
                setState { copy(installationDate = event.installationDate) }
            }
            is ModifyFilterContract.Event.ModifyFilter -> {
                if (validateInput()) {
                    with(viewState.value){
                        setEffect { ModifyFilterContract.Effect.FilterModified(Filter(
                            type, installationDate, lifeSpan
                        ), modifiedFilter) }
                    }
                    setState { initialState }
                } else {
                    //TODO add error handling
                }
            }
            is ModifyFilterContract.Event.RemoveFilter -> {
                setEffect { ModifyFilterContract.Effect.FilterRemoved(viewState.value.modifiedFilter) }
            }
        }
    }

    private fun validateInput() = true

    class Factory(private val modifiedFilter: Filter) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ModifyFilterViewModel(
                modifiedFilter
            ) as T
        }
    }
}
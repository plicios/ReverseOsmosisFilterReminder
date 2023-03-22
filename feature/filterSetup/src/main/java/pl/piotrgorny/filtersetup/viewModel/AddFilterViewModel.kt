package pl.piotrgorny.filtersetup.viewModel

import pl.piotrgorny.filtersetup.contract.AddFilterContract
import pl.piotrgorny.model.Filter
import pl.piotrgorny.mvi.MviBaseViewModel

class AddFilterViewModel : MviBaseViewModel<
        AddFilterContract.Event,
        AddFilterContract.State,
        AddFilterContract.Effect
>() {
    override fun initialState(): AddFilterContract.State = AddFilterContract.State()

    override fun handleEvents(event: AddFilterContract.Event) {
        when(event) {
            is AddFilterContract.Event.NameChange -> {
                setState { copy(name = event.name) }
            }
            is AddFilterContract.Event.TypeChange -> {
                setState { copy(type = event.type) }
            }
            is AddFilterContract.Event.LifeSpanChange -> {
                setState { copy(lifeSpan = event.lifeSpan) }
            }
            is AddFilterContract.Event.InstallationDateChange -> {
                setState { copy(installationDate = event.installationDate) }
            }
            is AddFilterContract.Event.AddFilter -> {
                if (validateInput()) {
                    with(viewState.value){
                        setEffect { AddFilterContract.Effect.FilterAdded(Filter(name, lifeSpan!!, installationDate!!, type!!)) }
                    }
                    setState { initialState() }
                } else {
                    //TODO add error handling
                }
            }
        }
    }

    private fun validateInput() = true
}
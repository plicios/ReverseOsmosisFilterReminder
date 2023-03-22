package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.mock.InMemoryFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.AddFilterSetupContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.MviBaseViewModel

class AddFilterSetupViewModel(private val repository: FilterSetupRepository) : MviBaseViewModel<
        AddFilterSetupContract.Event,
        AddFilterSetupContract.State,
        AddFilterSetupContract.Effect
        >() {
    override fun initialState(): AddFilterSetupContract.State =
        AddFilterSetupContract.State(isLoading = true)

    override fun handleEvents(event: AddFilterSetupContract.Event) {
        when(event){
            is AddFilterSetupContract.Event.AddFilterSetup -> {
                if(validateInput()){
                    saveFilterSetup()
                    setEffect { AddFilterSetupContract.Effect.Navigation.BackToFilterSetups }
                } else {
                    //TODO add error handling
                }
            }
            is AddFilterSetupContract.Event.NameChange -> {
                setState { copy(name = event.name) }
            }
            is AddFilterSetupContract.Event.AddFilter -> {
                setState { copy(filters = filters + event.filter, isAddFilterDialogOpen = false) }
            }
            is AddFilterSetupContract.Event.RequestAddFilter -> {
                setState { copy(isAddFilterDialogOpen = true) }
            }
            is AddFilterSetupContract.Event.DismissAddFilter -> {
                setState { copy(isAddFilterDialogOpen = false) }
            }
        }
    }

    private fun validateInput() = true

    private fun saveFilterSetup(){
        viewState.value.toFilterSetup()?.let {
            viewModelScope.launch {
                repository.addFilterSetup(it)
            }
        }
    }

    private fun AddFilterSetupContract.State.toFilterSetup() : FilterSetup? {
        if(this.name.isNotEmpty()){
            return FilterSetup(this.name, this.filters)
        }
        return null
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddFilterSetupViewModel(InMemoryFilterSetupRepository) as T
        }
    }
}
package pl.piotrgorny.filtersetup.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.AddFilterSetupContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.MviBaseViewModel

class AddFilterSetupViewModel(private val repository: FilterSetupRepository) : MviBaseViewModel<
        AddFilterSetupContract.Event,
        AddFilterSetupContract.State,
        AddFilterSetupContract.Effect
        >(AddFilterSetupContract.State(isLoading = true)) {

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
                setState { copy(filters = filters + event.filter, isAddingFilter = false) }
            }
            is AddFilterSetupContract.Event.RequestAddFilter -> {
                setState { copy(isAddingFilter = true) }
            }
            is AddFilterSetupContract.Event.DismissAddFilter -> {
                setState { copy(isAddingFilter = false) }
            }
            is AddFilterSetupContract.Event.RequestModifyFilter -> {
                setState { copy(filterBeingModified = event.filter) }
            }
            is AddFilterSetupContract.Event.DismissModifyFilter -> {
                setState { copy(filterBeingModified = null) }
            }
            is AddFilterSetupContract.Event.ModifyFilter -> {
                setState { copy(filters = filters - event.oldFilter + event.newFilter, filterBeingModified = null) }
            }
            is AddFilterSetupContract.Event.RemoveFilter -> {
                setState { copy(filters = filters - event.filter, filterBeingModified = null) }
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

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddFilterSetupViewModel(
//                InMemoryFilterSetupRepository
                DatabaseFilterSetupRepository.instance(
                    context
                )
            ) as T
        }
    }
}
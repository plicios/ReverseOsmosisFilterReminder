package pl.piotrgorny.filtersetup.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import pl.piotrgorny.common.remove
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.AddOrModifyFilterSetupContract
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.MviBaseViewModel
import java.util.*

class AddOrModifyFilterSetupViewModel(private val filterSetupId: Long?, private val repository: FilterSetupRepository) : MviBaseViewModel<
        AddOrModifyFilterSetupContract.Event,
        AddOrModifyFilterSetupContract.State,
        AddOrModifyFilterSetupContract.Effect
        >(AddOrModifyFilterSetupContract.State(isLoading = true)) {

    init {
        filterSetupId?.let {
            viewModelScope.launch {
                repository.getFilterSetup(it).collect{
                    it?.let {
                        setState { AddOrModifyFilterSetupContract.State(it) }
                    }
                }
            }
        }
    }

    override fun handleEvents(event: AddOrModifyFilterSetupContract.Event) {
        when(event){
            is AddOrModifyFilterSetupContract.Event.AddOrModifyFilterSetup -> {
                if(validateInput()){
                    saveFilterSetup()
                    setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.BackToFilterSetups }
                } else {
                    //TODO add error handling
                }
            }
            is AddOrModifyFilterSetupContract.Event.RemoveFilterSetup -> {
                deleteFilterSetup()
                setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.BackToFilterSetups }
            }
            is AddOrModifyFilterSetupContract.Event.RequestModifyFilterSetup -> {
                setState { copy(stateType =  AddOrModifyFilterSetupContract.State.Type.Modify) }
            }
            is AddOrModifyFilterSetupContract.Event.RequestRemoveFilterSetup -> {
                filterSetupId?.let { setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenRemoveFilterSetupDialog(it) } }
            }
            is AddOrModifyFilterSetupContract.Event.NameChange -> {
                setState { copy(name = event.name) }
            }
            is AddOrModifyFilterSetupContract.Event.TypeChange -> {
                setState { copy(type = event.type) }
                onTypeChange(event.type)
            }
            is AddOrModifyFilterSetupContract.Event.RequestAddFilter -> {
                setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenAddOrModifyFilterDialog() }
            }
            is AddOrModifyFilterSetupContract.Event.RequestModifyFilter -> {
                setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenAddOrModifyFilterDialog(
                    viewState.value.filters.indexOf(event.filter),
                    event.filter
                ) }
            }
            is AddOrModifyFilterSetupContract.Event.RequestRemoveFilter -> {
                setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenRemoveFilterDialog(
                    viewState.value.filters.indexOf(event.filter)
                ) }
            }
            is AddOrModifyFilterSetupContract.Event.RequestRenewFilters -> {
                filterSetupId?.let { setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenRenewFiltersDialog(it) } }
            }
            is AddOrModifyFilterSetupContract.Event.AddFilter -> {
                setState { copy(filters = filters + event.filter) }
            }
            is AddOrModifyFilterSetupContract.Event.ModifyFilter -> {
                setState { copy(filters = filters.toMutableList().apply {
                    this[event.index] = event.newFilter
                }) }
            }
            is AddOrModifyFilterSetupContract.Event.RemoveFilter -> {
                setState { copy(filters = filters.toMutableList().apply {
                    this.removeAt(event.index)
                }) }
            }
        }
    }

    private fun onTypeChange(newType: FilterSetup.Type) {
        when(newType) {
            FilterSetup.Type.Custom -> {}
            else -> {
                val filterSetup = FilterSetup(newType, viewState.value.filters)
                addMissingFilters(filterSetup.missingFilterTypes)
                removeObsoleteFilters(filterSetup.obsoleteFilters)
            }
        }
    }

    private fun addMissingFilters(missingFilterTypes: List<Filter.Type>) {
        setState { copy(filters = filters + missingFilterTypes.map { Filter(it) }) }
    }

    private fun removeObsoleteFilters(obsoleteFilters: List<Filter>) {
        setState { copy(filters = filters.remove(obsoleteFilters)) }
    }

    private fun validateInput() = true

    private fun saveFilterSetup() {
        viewState.value.toFilterSetup()?.let {
            viewModelScope.launch {
                if(filterSetupId != null)
                    repository.updateFilterSetup(it.copy(id = filterSetupId))
                else
                    repository.addFilterSetup(it)
            }
        }
    }

    private fun deleteFilterSetup() {
        filterSetupId?.let {
            viewModelScope.launch {
                repository.deleteFilterSetup(it)
            }
        }
    }

    private fun AddOrModifyFilterSetupContract.State.toFilterSetup() : FilterSetup? =
        if(this.name.isNotEmpty())
            FilterSetup(this.name, this.type, this.filters)
        else
            null

    class Factory(private val filterSetupId: Long?,
        private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddOrModifyFilterSetupViewModel(
                filterSetupId,
                DatabaseFilterSetupRepository.instance(
                    context
                )
            ) as T
        }
    }
}
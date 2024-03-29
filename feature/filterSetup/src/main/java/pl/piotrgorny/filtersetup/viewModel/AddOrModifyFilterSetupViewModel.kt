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
import pl.piotrgorny.mvi.Error
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
                    viewState.value.toFilterSetup()?.let {
                        saveFilterSetup(it)
                        setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.BackToFilterSetups }
                    }
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
                if(viewState.value.checkValidity) {
                    validateInput()
                }
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
                    event.index,
                    event.filter
                ) }
            }
            is AddOrModifyFilterSetupContract.Event.RequestRemoveFilter -> {
                setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenRemoveFilterDialog(
                    event.index
                ) }
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
            is AddOrModifyFilterSetupContract.Event.RequestRenewFilters -> {
                filterSetupId?.let {
                    setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.OpenRenewFiltersView(it) }
                }
            }
            is AddOrModifyFilterSetupContract.Event.RenewFilters -> {
                val renewedFilters = renewedFilters()
                viewState.value.toFilterSetup(renewedFilters)?.let {
                    saveFilterSetup(it)
                    setEffect { AddOrModifyFilterSetupContract.Effect.Navigation.BackToFilterSetups }
                }
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

    private fun validateInput() = with(viewState.value) {
        var valid = true
        setState { copy(checkValidity = true).also { clearErrors() } }
        if (name.isBlank()) {
            setState { copy().also { setError("nameError", Error.NoValue) } }
            valid = false
        }
        if (filters.isEmpty()) {
            setState { copy().also { setError("filtersError", Error.NoValue) } }
            valid = false
        }
        valid
    }

    private fun renewedFilters() : List<Filter> {
        return viewState.value.filters.map {
            if(it.isExpired)
                it.copy(installationDate = LocalDate())
            else
                it
        }
    }
    private fun saveFilterSetup(filterSetup: FilterSetup) {
        viewModelScope.launch {
            if(filterSetupId != null)
                repository.updateFilterSetup(filterSetup.copy(id = filterSetupId))
            else
                repository.addFilterSetup(filterSetup)
        }
    }

    private fun deleteFilterSetup() {
        filterSetupId?.let {
            viewModelScope.launch {
                repository.deleteFilterSetup(it)
            }
        }
    }

    private fun AddOrModifyFilterSetupContract.State.toFilterSetup(filters: List<Filter>? = null) : FilterSetup? =
        if(this.name.isNotEmpty())
            FilterSetup(this.name, this.type, filters ?: this.filters)
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
package pl.piotrgorny.filtersetup.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.RenewFiltersContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.MviBaseViewModel

class RenewFiltersViewModel(
    filterSetupId: Long, private val repository: FilterSetupRepository
    ) : MviBaseViewModel<
        RenewFiltersContract.Event,
        RenewFiltersContract.State,
        RenewFiltersContract.Effect
>(RenewFiltersContract.State(isLoading = true)) {

    init {
        viewModelScope.launch {
            repository.getFilterSetup(filterSetupId).collect{
                it?.let {
                    setState { RenewFiltersContract.State(it) }
                }
            }
        }
    }

    override fun handleEvents(event: RenewFiltersContract.Event) {
        when(event) {
            is RenewFiltersContract.Event.RenewalDateChanged ->
                setState { copy(renewalDate = event.renewalDate) }
            is RenewFiltersContract.Event.FilterSelectionChange ->
                setState { copy(filterSelection = filterSelection.toMutableMap().apply {
                    this[event.filter] = event.selected
                }) }
            is RenewFiltersContract.Event.RenewFilters -> {
                saveFilterSetup()
                setEffect { RenewFiltersContract.Effect.Navigation.BackToFilterSetups }
            }
        }
    }

    private fun saveFilterSetup() {
        viewState.value.toFilterSetup()?.let {
            viewModelScope.launch {
                repository.updateFilterSetup(it)
            }
        }
    }

    private fun RenewFiltersContract.State.toFilterSetup() : FilterSetup? =
        if(this.filterSetup != null)
            filterSetup.copy(
                filters = viewState.value.filterSelection.map {
                    if(it.value)
                        it.key.copy(installationDate = viewState.value.renewalDate)
                    else
                        it.key
                }
            )
        else
            null

    class Factory(
        private val filterSetupId: Long,
        private val context: Context
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RenewFiltersViewModel(
                filterSetupId, DatabaseFilterSetupRepository.instance(context)
            ) as T
        }
    }
}
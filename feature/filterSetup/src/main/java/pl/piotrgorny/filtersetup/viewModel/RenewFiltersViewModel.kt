package pl.piotrgorny.filtersetup.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import pl.piotrgorny.common.replace
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.RenewFiltersContract
import pl.piotrgorny.model.FilterSetup
import pl.piotrgorny.mvi.MviBaseViewModel
import java.util.*

class RenewFiltersViewModel(private val filterSetupId: Long, private val repository: FilterSetupRepository) : MviBaseViewModel<
        RenewFiltersContract.Event,
        RenewFiltersContract.State,
        RenewFiltersContract.Effect
        >(RenewFiltersContract.State()) {

    init {
        viewModelScope.launch {
            repository.getFilterSetup(filterSetupId).collect{
                it?.let {
                    setState { RenewFiltersContract.State(it, it.filters.map { filter -> filter to filter.isExpired }) }
                }
            }
        }
    }

    override fun handleEvents(event: RenewFiltersContract.Event) {
        when(event){
            is RenewFiltersContract.Event.FilterDeselected ->
                setState { copy(filterSelection = filterSelection.replace(event.filter to false) {event.filter == it.first}) }
            is RenewFiltersContract.Event.FilterSelected ->
                setState { copy(filterSelection = filterSelection.replace(event.filter to true) {event.filter == it.first}) }
            is RenewFiltersContract.Event.RenewFilters -> {
                viewState.value.toFilterSetup()?.let(::saveFilterSetup)
                setEffect { RenewFiltersContract.Effect.FiltersRenewed }
            }
        }
    }

    private fun saveFilterSetup(filterSetup: FilterSetup) {
        viewModelScope.launch {
            repository.updateFilterSetup(filterSetup.copy(id = filterSetupId))
        }
    }

    private fun RenewFiltersContract.State.toFilterSetup() : FilterSetup? =
        if(this.filterSetup != null)
            this.filterSetup.copy(filters = this.filterSelection.map {
                if(it.second)
                    it.first.copy(installationDate = LocalDate())
                else
                    it.first
            })
        else
            null

    class Factory(private val filterSetupId: Long,
        private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RenewFiltersViewModel(
                filterSetupId,
                DatabaseFilterSetupRepository.instance(
                    context
                )
            ) as T
        }
    }
}
package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.mock.InMemoryFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.mvi.MviBaseViewModel

class FilterSetupsViewModel(private val repository: FilterSetupRepository) : MviBaseViewModel<
        FilterSetupsContract.Event,
        FilterSetupsContract.State,
        FilterSetupsContract.Effect>() {
    init {
        viewModelScope.launch { getFilterSetups() }
    }

    override fun setInitialState(): FilterSetupsContract.State =
        FilterSetupsContract.State(isLoading = true)

    override fun handleEvents(event: FilterSetupsContract.Event) {
        when (event) {
            is FilterSetupsContract.Event.FilterSetupSelection -> {
                setEffect { FilterSetupsContract.Effect.Navigation.ToFilterSetupDetails(event.filterSetup) }
            }
            is FilterSetupsContract.Event.AddFilterSetup -> {
                setEffect { FilterSetupsContract.Effect.Navigation.ToAddFilterSetup }
            }
        }
    }

    private suspend fun getFilterSetups() {
        repository.getFilterSetups().collect {
            setState { copy(filterSetups = it, isLoading = false) }
            setEffect { FilterSetupsContract.Effect.ToastDataWasLoaded }
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilterSetupsViewModel(InMemoryFilterSetupRepository) as T
        }
    }
}
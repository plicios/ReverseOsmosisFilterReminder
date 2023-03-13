package pl.piotrgorny.filtersetup.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract
import pl.piotrgorny.mvi.MviBaseViewModel

class FilterSetupViewModel(private val repository: FilterSetupRepository) : MviBaseViewModel<
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
        }
    }

    private suspend fun getFilterSetups() {
        val filterSetups = repository.getFilterSetups()
        setState { copy(filterSetups = filterSetups, isLoading = false) }
        setEffect { FilterSetupsContract.Effect.ToastDataWasLoaded }
    }
}
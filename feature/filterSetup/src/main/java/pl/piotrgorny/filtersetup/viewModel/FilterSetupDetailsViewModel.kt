package pl.piotrgorny.filtersetup.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.piotrgorny.data.FilterSetupRepository
import pl.piotrgorny.data.database.DatabaseFilterSetupRepository
import pl.piotrgorny.filtersetup.contract.FilterSetupDetailsContract
import pl.piotrgorny.mvi.MviBaseViewModel

class FilterSetupDetailsViewModel(private val filterSetupRepository: FilterSetupRepository, private val filterSetupId: Long) : MviBaseViewModel<
        FilterSetupDetailsContract.Event,
        FilterSetupDetailsContract.State,
        FilterSetupDetailsContract.Effect
        >(FilterSetupDetailsContract.State()) {

    init {
        viewModelScope.launch {
            filterSetupRepository.getFilterSetup(filterSetupId).collect {
                setState { copy(filterSetup = it, isLoading = false) }
            }
        }
    }

    override fun handleEvents(event: FilterSetupDetailsContract.Event) {
//        when(event){
//        }
    }

    class Factory(private val context: Context, private val filterSetupId: Long) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilterSetupDetailsViewModel(
//                InMemoryFilterSetupRepository
                DatabaseFilterSetupRepository.instance(context),
                filterSetupId
            ) as T
        }
    }
}
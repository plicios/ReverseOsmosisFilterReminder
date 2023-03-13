package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.filtersetup.contract.FilterSetupsContract

@Composable
fun FilterSetupsView(
    state: FilterSetupsContract.State,
    effectFlow: Flow<FilterSetupsContract.Effect>?,
    onEventSent: (event: FilterSetupsContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FilterSetupsContract.Effect.Navigation) -> Unit
) {

}
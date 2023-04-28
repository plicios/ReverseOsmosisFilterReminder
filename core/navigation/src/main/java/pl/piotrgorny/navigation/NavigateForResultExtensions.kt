package pl.piotrgorny.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetResult(key: String, onResult: @Composable (T) -> Unit){
    val valueScreenResult = currentBackStackEntry?.savedStateHandle
        ?.getLiveData<T>(key)?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)
    }
}

@Composable
fun <T> NavController.GetResultOnce(key: String, onResult: @Composable (T) -> Unit){
    val valueScreenResult = currentBackStackEntry?.savedStateHandle
        ?.getLiveData<T>(key)?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)
        currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    }
}

fun <T> NavController.popBackStackWithResult(key: String, result: T){
    previousBackStackEntry
            ?.savedStateHandle
            ?.set(key, result)
    popBackStack()
}

fun <T> NavBackStackEntry.get(key: String, converter: (String) -> T) = arguments?.getString(key)?.let(converter)
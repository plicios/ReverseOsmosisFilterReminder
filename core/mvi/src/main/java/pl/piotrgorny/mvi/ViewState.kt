package pl.piotrgorny.mvi

interface ViewState

abstract class ValidationViewState : ViewState {
    abstract val checkValidity: Boolean
    private val errors = mutableMapOf<String, Error>()

    fun clearErrors() {
        errors.clear()
    }
    fun setError(key: String, error: Error) {
        errors[key] = error
    }

    fun getError(key: String) : Error?  = errors[key]
}

enum class Error {
    NoValue,
    IncorrectValue
}
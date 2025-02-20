package id.elutility.core.model

sealed interface Result<out T> {
    data object Loading: Result<Nothing>
    data object Empty: Result<Nothing>
    data class Error(val message: String, val throwable: Throwable?):
        Result<Nothing>
    data class Success<out T>(val data: T): Result<T>
}
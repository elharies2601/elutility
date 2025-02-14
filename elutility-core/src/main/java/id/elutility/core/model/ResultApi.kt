package id.elutility.core.model

sealed interface ResultApi<out T> {
    data object Loading: ResultApi<Nothing>
    data object Empty: ResultApi<Nothing>
    data class Error(val message: String, val throwable: Throwable?): ResultApi<Nothing>
    data class Success<out T>(val data: T): ResultApi<T>
}
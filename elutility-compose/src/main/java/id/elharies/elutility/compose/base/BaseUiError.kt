package id.elharies.elutility.compose.base

sealed interface BaseUiError {
    data class Network(val msg: String) : BaseUiError
    data class Unknown(val msg: String) : BaseUiError
}
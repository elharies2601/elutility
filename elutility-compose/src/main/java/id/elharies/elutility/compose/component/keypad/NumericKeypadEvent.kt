package id.elharies.elutility.compose.component.keypad

sealed interface NumericKeypadEvent {
    data class NumberPressed(val value: String) : NumericKeypadEvent
    data class OperationPressed(val op: String) : NumericKeypadEvent
    object DecimalPressed : NumericKeypadEvent
    object EqualsPressed : NumericKeypadEvent
    object ClearPressed : NumericKeypadEvent
}
package id.elharies.elutility.compose.component.keypad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NumericKeypadState(
    val input: String = "",
    val operation: String? = null,
    val result: String = ""
) {
    val cleanResult: Double
        get() = result.toDoubleOrNull() ?: 0.0
}

class NumericKeypadViewModel : ViewModel() {

    private val _state = MutableStateFlow(NumericKeypadState())
    val state: StateFlow<NumericKeypadState> = _state

    init {
        // Initial state
        _state.value = NumericKeypadState()
    }

    fun onEvent(event: NumericKeypadEvent) {
        when (event) {
            is NumericKeypadEvent.NumberPressed -> handleNumberPressed(event.value)
            is NumericKeypadEvent.OperationPressed -> handleOperationPressed(event.op)
            is NumericKeypadEvent.DecimalPressed -> handleDecimalPressed()
            is NumericKeypadEvent.EqualsPressed -> handleEqualsPressed()
            is NumericKeypadEvent.ClearPressed -> handleClearPressed()
        }
    }

    private fun handleNumberPressed(value: String) {
        val current = _state.value.input
        if (current == "0" && value != ".") {
            _state.update { it.copy(input = value) }
        } else {
            _state.update { it.copy(input = current + value) }
        }
    }

    private fun handleOperationPressed(op: String) {
        val current = _state.value.input
        if (current.isEmpty()) return

        val state = _state.value
        if (state.operation != null) {
            handleEqualsPressed()
        }
        _state.update {
            it.copy(
                operation = op,
                input = ""
            )
        }
    }

    private fun handleDecimalPressed() {
        val current = _state.value.input
        if (!current.contains(".")) {
            _state.update { it.copy(input = "$current.") }
        }
    }

    private fun handleEqualsPressed() {
        val state = _state.value
        val input = state.input
        if (input.isEmpty() || state.operation == null) return

        val value = input.toDoubleOrNull() ?: return
        val current = state.result.toDoubleOrNull() ?: 0.0

        val result = when (state.operation) {
            "+" -> current + value
            "-" -> current - value
            "*" -> current * value
            "/" -> if (value != 0.0) current / value else Double.POSITIVE_INFINITY
            else -> return
        }

        _state.update {
            it.copy(
                input = result.toString(),
                operation = null,
                result = result.toString()
            )
        }
    }

    private fun handleClearPressed() {
        _state.update { NumericKeypadState() }
    }
}
package id.elharies.elutility.compose.component.keypad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * Tampilan keyboard numerik (0-9) lengkap dengan tombol operasi matematika
 * (`+`, `-`, `*`, `/`), titik desimal, sama dengan (`=`), dan hapus (`C`).
 *
 * Komponen ini bersifat stateless: setiap tombol hanya memicu callback yang
 * diberikan, sehingga logika input dan perhitungan sepenuhnya ditentukan oleh
 * pemanggil. Cocok dipasangkan dengan [androidx.compose.foundation.text.BasicTextField]
 * atau [androidx.compose.material3.OutlinedTextField] sebagai input alternatif.
 *
 * @param onNumberClick dipanggil saat tombol angka `0`-`9` ditekan; menerima angka sebagai [String]
 * @param onOperationClick dipanggil saat tombol operator ditekan; menerima `"+"`, `"-"`, `"*"`, atau `"/"`
 * @param onDecimalClick dipanggil saat tombol titik desimal (`.`) ditekan
 * @param onEqualsClick dipanggil saat tombol sama dengan (`=`) ditekan
 * @param onClearClick dipanggil saat tombol hapus (`C`) ditekan
 * @param modifier [Modifier] opsional untuk mengubah tata letak keypad
 *
 * Contoh penggunaan dengan [NumericKeypadViewModel]:
 *
 * ```kotlin
 * @Composable
 * fun CalculatorScreen(viewModel: NumericKeypadViewModel) {
 *     val state by viewModel.state.collectAsState()
 *
 *     Column(modifier = Modifier.fillMaxSize()) {
 *         Text(
 *             text = state.input.ifBlank { state.result.ifBlank { "0" } },
 *             modifier = Modifier.align(Alignment.End)
 *         )
 *         NumericKeypad(
 *             onNumberClick = { viewModel.onEvent(NumericKeypadEvent.NumberPressed(it)) },
 *             onOperationClick = { viewModel.onEvent(NumericKeypadEvent.OperationPressed(it)) },
 *             onDecimalClick = { viewModel.onEvent(NumericKeypadEvent.DecimalPressed) },
 *             onEqualsClick = { viewModel.onEvent(NumericKeypadEvent.EqualsPressed) },
 *             onClearClick = { viewModel.onEvent(NumericKeypadEvent.ClearPressed) },
 *         )
 *     }
 * }
 * ```
 *
 * Contoh penggunaan langsung dengan state lokal:
 *
 * ```kotlin
 * var expression by remember { mutableStateOf("") }
 *
 * NumericKeypad(
 *     onNumberClick = { expression += it },
 *     onOperationClick = { expression += it },
 *     onDecimalClick = { expression += "." },
 *     onEqualsClick = { expression = evaluate(expression) },
 *     onClearClick = { expression = "" },
 * )
 * ```
 */
@Composable
fun NumericKeypad(
    onNumberClick: (String) -> Unit,
    onOperationClick: (String) -> Unit,
    onDecimalClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Keypad grid
        KeypadGrid(
            onNumberClick = onNumberClick,
            onOperationClick = onOperationClick,
            onDecimalClick = onDecimalClick,
            onEqualsClick = onEqualsClick,
            onClearClick = onClearClick
        )
    }
}

@Composable
private fun KeypadGrid(
    onNumberClick: (String) -> Unit,
    onOperationClick: (String) -> Unit,
    onDecimalClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    // Define the keypad layout
    val rows = listOf(
        listOf("7" to Type.Number, "8" to Type.Number, "9" to Type.Number, "/" to Type.Operation),
        listOf("4" to Type.Number, "5" to Type.Number, "6" to Type.Number, "*" to Type.Operation),
        listOf("1" to Type.Number, "2" to Type.Number, "3" to Type.Number, "-" to Type.Operation),
        listOf("0" to Type.Number, "." to Type.Decimal, "=" to Type.Equals, "+" to Type.Operation),
        listOf("C" to Type.Clear)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                row.forEach { (label, type) ->
                    when (type) {
                        Type.Number -> NumberButton(modifier = Modifier
                            .weight(1f)
                            .width(0.dp), label = label) { onNumberClick(label) }
                        Type.Operation -> OperationButton(modifier = Modifier
                            .weight(1f)
                            .width(0.dp), label = label) { onOperationClick(label) }
                        Type.Decimal -> DecimalButton(modifier = Modifier.weight(1f)
                            .width(0.dp)) { onDecimalClick() }
                        Type.Equals -> EqualsButton(modifier = Modifier
                            .weight(1f)
                            .width(0.dp)) { onEqualsClick() }
                        Type.Clear -> ClearButton { onClearClick() }
                    }
                }
            }
        }
    }
}

private enum class Type { Number, Operation, Decimal, Equals, Clear }

@Composable
private fun NumberButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(label)
    }
}

@Composable
private fun OperationButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(label)
    }
}

@Composable
private fun DecimalButton(modifier: Modifier = Modifier,onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(".")
    }
}

@Composable
private fun EqualsButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text("=")
    }
}

@Composable
private fun ClearButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text("C")
    }
}

// Preview
@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
private fun PreviewNumericKeypad() {
    var displayText by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = displayText,
            fontSize = 24.sp,
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumericKeypad(
            onNumberClick = { value ->
                displayText = if (displayText == "0") value else displayText + value
            },
            onOperationClick = { op ->
                displayText = "$displayText $op "
            },
            onDecimalClick = { displayText = "$displayText." },
            onEqualsClick = { /* implement equals */ },
            onClearClick = { displayText = "0" }
        )
    }
}
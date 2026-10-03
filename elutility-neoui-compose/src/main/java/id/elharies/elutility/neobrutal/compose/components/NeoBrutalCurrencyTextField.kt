package id.elharies.elutility.neobrutal.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalShapes
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalSpacing
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme
import java.text.NumberFormat
import java.util.Locale

private val idrLocale = Locale("in", "ID")
private val idrFormatter = NumberFormat.getNumberInstance(idrLocale)

private fun Long.formatIdr(): String = idrFormatter.format(this)

private fun String.parseRawDigits(): String = this.filter { it.isDigit() }

/**
 * Neo-brutalist currency input field with Indonesian Rupiah formatting.
 *
 * Text is formatted on each change using `NumberFormat` and the cursor
 * position is kept at the end of the text, which works for both typing
 * and deleting characters.
 *
 * @param value Current numeric value (in Rupiah) represented as Long.
 * @param label Label displayed above the field.
 * @param onValueChange Callback invoked with the raw numeric value.
 * @param modifier Modifier for the outer container.
 * @param showPrefix Whether the `Rp` prefix is shown.
 * @param enabled Whether the field is editable.
 * @param isError Whether to display error state styling.
 */
@Composable
fun NeoBrutalCurrencyTextField(
    value: Long,
    label: String,
    onValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    showPrefix: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false
) {
    val brutal = LocalBrutalColors.current

    var textFieldValue by remember(value) {
        mutableStateOf(
            TextFieldValue(
                text = if (value == 0L) "" else value.formatIdr(),
                selection = TextRange(if (value == 0L) 0 else value.formatIdr().length)
            )
        )
    }

    var previousTextFieldValue by remember {
        mutableStateOf(textFieldValue)
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) brutal.ink else brutal.disabledText
        )

        Spacer(modifier = Modifier.height(NeoBrutalSpacing.Sm))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val digits = newValue.text.parseRawDigits()
                val numeric = digits.toLongOrNull() ?: 0L
                val formatted = if (numeric == 0L) "" else numeric.formatIdr()

                val isDeleting = newValue.text.length <= previousTextFieldValue.text.length
                val newSelection = if (isDeleting) {
                    formatted.length
                } else {
                    val oldLen = previousTextFieldValue.text.length
                    val newLen = formatted.length
                    val delta = newLen - oldLen
                    val baseSelection = newValue.selection.start
                    val result = if (baseSelection >= oldLen) {
                        formatted.length
                    } else {
                        (baseSelection + delta).coerceIn(0, newLen)
                    }
                    result
                }

                textFieldValue = TextFieldValue(
                    text = formatted,
                    selection = TextRange(newSelection)
                )
                previousTextFieldValue = textFieldValue
                onValueChange(numeric)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isError,
            prefix = if (showPrefix) {
                {
                    Text(
                        text = "Rp",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (enabled) brutal.ink else brutal.disabledText
                    )
                }
            } else null,
            placeholder = {
                Text(
                    text = "0",
                    style = MaterialTheme.typography.bodyLarge,
                    color = brutal.mutedText
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = NeoBrutalShapes.ExtraSmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = brutal.surface,
                unfocusedContainerColor = brutal.surface,
                disabledContainerColor = brutal.disabledBackground,
                errorContainerColor = brutal.surface,
                focusedTextColor = brutal.ink,
                unfocusedTextColor = brutal.ink,
                disabledTextColor = brutal.disabledText,
                errorTextColor = brutal.ink,
                cursorColor = brutal.ink,
                errorCursorColor = brutal.danger,
                focusedBorderColor = brutal.ink,
                unfocusedBorderColor = brutal.ink,
                disabledBorderColor = brutal.disabledText,
                errorBorderColor = brutal.danger
            ),
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalCurrencyTextFieldPreview() {
    NeoBrutalTheme {
        var amount by remember { mutableStateOf(0L) }
        NeoBrutalCurrencyTextField(
            value = amount,
            label = "Harga",
            onValueChange = { amount = it }
        )
    }
}

@Preview(showBackground = true, name = "With Value")
@Composable
private fun NeoBrutalCurrencyTextFieldFilledPreview() {
    NeoBrutalTheme {
        NeoBrutalCurrencyTextField(
            value = 1500000L,
            label = "Harga",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true, name = "Without Prefix")
@Composable
private fun NeoBrutalCurrencyTextFieldNoPrefixPreview() {
    NeoBrutalTheme {
        NeoBrutalCurrencyTextField(
            value = 1500000L,
            label = "Harga",
            showPrefix = false,
            onValueChange = {}
        )
    }
}

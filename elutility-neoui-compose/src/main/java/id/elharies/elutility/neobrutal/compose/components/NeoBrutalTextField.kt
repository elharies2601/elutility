package id.elharies.elutility.neobrutal.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalShapes
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalSpacing
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme

/**
 * Neo-brutalist outlined text field.
 *
 * @param value Current text value.
 * @param label Text label displayed above the field.
 * @param modifier Modifier for the outer container.
 * @param placeholder Optional placeholder text.
 * @param enabled Whether the field is editable.
 * @param singleLine Whether input is limited to one line.
 * @param isError Whether to display error state.
 * @param onValueChange Callback when text changes.
 */
@Composable
fun NeoBrutalTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    onValueChange: (String) -> Unit = {}
) {
    val brutal = LocalBrutalColors.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) brutal.ink else brutal.disabledText
        )

        Spacer(
            modifier = Modifier.height(NeoBrutalSpacing.Sm)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = singleLine,
            isError = isError,
            placeholder = {
                if (placeholder != null) {
                    Text(
                        text = placeholder,
                        color = brutal.mutedText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
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
private fun NeoBrutalTextFieldPreview() {
    NeoBrutalTheme {
        NeoBrutalTextField(
            value = "",
            label = "Label",
            placeholder = "Placeholder"
        )
    }
}

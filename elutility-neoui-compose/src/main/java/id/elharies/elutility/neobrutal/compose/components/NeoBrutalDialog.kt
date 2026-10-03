package id.elharies.elutility.neobrutal.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalSpacing
import id.elharies.elutility.neobrutal.compose.foundation.brutalCard
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme

/**
 * Neo-brutalist dialog with title and optional description.
 *
 * Supports single or dual button layout:
 * - One button: when `negativeLabel` is null.
 * - Two buttons (negative/positive): when `negativeLabel` is provided.
 *
 * @param title Dialog title.
 * @param positiveLabel Primary action label.
 * @param onPositiveClick Callback when positive button is tapped.
 * @param modifier Modifier for the outer container.
 * @param description Optional body text under title.
 * @param negativeLabel Secondary action label; triggers dual-button layout.
 * @param onNegativeClick Callback when negative button is tapped.
 * @param onDismissRequest Callback when dismiss is requested (outside click, back).
 * @param dismissOnClickOutside Whether tapping outside dismisses the dialog.
 */
@Composable
fun NeoBrutalDialog(
    title: String,
    positiveLabel: String,
    onPositiveClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    negativeLabel: String? = null,
    onNegativeClick: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    dismissOnClickOutside: Boolean = true
) {
    val brutal = LocalBrutalColors.current

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = NeoBrutalSpacing.Xl)
                .brutalCard(
                    ink = brutal.ink,
                    surface = brutal.surface,
                    radius = 4.dp,
                    shadowOffset = 4.dp
                )
                .padding(NeoBrutalSpacing.Xl)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = brutal.ink
            )
            if (description != null) {
                Spacer(modifier = Modifier.height(NeoBrutalSpacing.Sm))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = brutal.mutedText
                )
            }
            Spacer(modifier = Modifier.height(NeoBrutalSpacing.Xl))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (negativeLabel != null) {
                    NeoBrutalOutlinedButton(
                        text = negativeLabel,
                        modifier = Modifier.weight(1f),
                        onClick = { onNegativeClick?.invoke() ?: onDismissRequest() }
                    )
                    Spacer(modifier = Modifier.width(NeoBrutalSpacing.Md))
                    NeoBrutalButton(
                        text = positiveLabel,
                        modifier = Modifier.weight(1f),
                        onClick = onPositiveClick
                    )
                } else {
                    NeoBrutalButton(
                        text = positiveLabel,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onPositiveClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalDialogSinglePreview() {
    NeoBrutalTheme {
        NeoBrutalDialog(
            title = "Hapus item?",
            description = "Tindakan ini tidak dapat dibatalkan.",
            positiveLabel = "Hapus",
            onPositiveClick = {},
            onDismissRequest = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalDialogTwoPreview() {
    NeoBrutalTheme {
        NeoBrutalDialog(
            title = "Keluar aplikasi?",
            description = "Perubahan yang belum disimpan akan hilang.",
            positiveLabel = "Keluar",
            onPositiveClick = {},
            negativeLabel = "Batal",
            onNegativeClick = {},
            onDismissRequest = {}
        )
    }
}

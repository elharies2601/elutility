package id.elharies.elutility.neobrutal.compose.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalBorders
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalSpacing
import id.elharies.elutility.neobrutal.compose.foundation.brutalCard
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme

/**
 * Neo-brutalist button with pressed-state shadow animation.
 *
 * @param text Label text to display.
 * @param modifier Modifier for the outer container.
 * @param containerColor Background fill color; defaults to accent when null.
 * @param enabled Whether the button is tappable.
 * @param radius Corner radius in dp.
 * @param borderWidth Border width in dp.
 * @param onClick Tap callback.
 */
@Composable
fun NeoBrutalButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    enabled: Boolean = true,
    radius: Dp = 4.dp,
    borderWidth: Dp = NeoBrutalBorders.Default,
    onClick: () -> Unit
) {
    val brutal = LocalBrutalColors.current
    val surfaceColor = containerColor ?: brutal.accent
    var pressed by remember { mutableStateOf(false) }

    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 1.dp else 4.dp,
        label = "brutalButtonShadow"
    )

    Box(
        modifier = modifier
            .semantics {
                role = Role.Button
                contentDescription = text
            }
            .defaultMinSize(minHeight = 48.dp)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onTap = { onClick() }
                )
            }
            .brutalCard(
                ink = if (enabled) brutal.ink else brutal.disabledText,
                surface = if (enabled) surfaceColor else brutal.disabledBackground,
                radius = radius,
                borderWidth = borderWidth,
                shadowOffset = shadowOffset
            )
            .padding(
                horizontal = NeoBrutalSpacing.Xl,
                vertical = NeoBrutalSpacing.Md
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) brutal.ink else brutal.disabledText,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalButtonPreview() {
    NeoBrutalTheme {
        NeoBrutalButton(text = "Save") {}
    }
}

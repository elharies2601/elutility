package id.elharies.elutility.neobrutal.compose.components

import android.R.attr.onClick
import android.R.attr.radius
import android.R.attr.shadowColor
import android.R.attr.text
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
import id.elharies.elutility.neobrutal.compose.foundation.brutalOutlined
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme

/**
 * Neo-brutalist outlined button with optional dynamic border, text, and shadow colors.
 *
 * @param text Label text to display.
 * @param modifier Modifier for the outer container.
 * @param enabled Whether the button is tappable.
 * @param radius Corner radius in dp.
 * @param borderWidth Border width in dp.
 * @param borderColor Border color; defaults to ink.
 * @param textColor Label color; defaults to ink.
 * @param shadowColor Shadow color; defaults to border color.
 * @param onClick Tap callback.
 */
@Composable
fun NeoBrutalOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    radius: Dp = 4.dp,
    borderWidth: Dp = NeoBrutalBorders.Default,
    color: Color? = null,
    onClick: () -> Unit
) {
    val brutal = LocalBrutalColors.current
    val effectiveBorderColor = color ?: brutal.ink
    val effectiveTextColor = color ?: brutal.ink
    val effectiveShadowColor = color ?: effectiveBorderColor
    var pressed by remember { mutableStateOf(false) }

    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 1.dp else 4.dp,
        label = "brutalOutlinedButtonShadow"
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
            .brutalOutlined(
                ink = if (enabled) effectiveShadowColor else brutal.disabledText,
                radius = radius,
                borderWidth = borderWidth,
                shadowOffset = shadowOffset,
                borderColor = if (enabled) effectiveBorderColor else brutal.disabledText,
                containerColor = brutal.surface
            )
            .padding(
                horizontal = NeoBrutalSpacing.Xl,
                vertical = NeoBrutalSpacing.Md
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) effectiveTextColor else brutal.disabledText,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalOutlinedButtonPreview() {
    NeoBrutalTheme {
        NeoBrutalOutlinedButton(text = "Cancel") {}
    }
}

@Preview(showBackground = true, name = "Custom Colors")
@Composable
private fun NeoBrutalOutlinedButtonCustomPreview() {
    NeoBrutalTheme {
        NeoBrutalOutlinedButton(
            text = "Save",
            color = Color(0xFFD4A017)
        ) {}
    }
}
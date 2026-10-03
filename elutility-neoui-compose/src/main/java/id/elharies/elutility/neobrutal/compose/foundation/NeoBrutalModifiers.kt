package id.elharies.elutility.neobrutal.compose.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/**
 * Applies neo-brutalist card styling: hard shadow, border, and background.
 *
 * The shadow is rendered via `drawBehind` with a hard offset (no blur),
 * matching the neo-brutalist aesthetic.
 *
 * @param ink Color for shadow and border.
 * @param surface Background fill color.
 * @param radius Corner radius in dp (default 4.dp per MD3 scale).
 * @param borderWidth Border width in dp (default 2.dp).
 * @param shadowOffset Shadow offset in dp (default 4.dp).
 */
fun Modifier.brutalCard(
    ink: Color,
    surface: Color,
    radius: Dp = 4.dp,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 4.dp
): Modifier = this
    .drawBehind {
        val offsetPx = shadowOffset.toPx()
        drawRoundRect(
            color = ink,
            topLeft = Offset(offsetPx, offsetPx),
            size = size,
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
    .background(surface, RoundedCornerShape(radius))
    .border(borderWidth, ink, RoundedCornerShape(radius))
    .padding(end = shadowOffset, bottom = shadowOffset)

/**
 * Applies neo-brutalist outlined styling: hard shadow and border (no fill by default).
 *
 * @param ink Color for shadow.
 * @param radius Corner radius in dp (default 4.dp).
 * @param borderWidth Border width in dp (default 2.dp).
 * @param shadowOffset Shadow offset in dp (default 4.dp).
 * @param borderColor Border color; defaults to `ink`.
 * @param containerColor Background fill color; defaults to white.
 */
fun Modifier.brutalOutlined(
    ink: Color,
    radius: Dp = 4.dp,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 4.dp,
    borderColor: Color = ink,
    containerColor: Color = Color.White
): Modifier = this
    .padding(end = shadowOffset, bottom = shadowOffset)
    .drawBehind {
        val offsetPx = shadowOffset.toPx()
        drawRoundRect(
            color = ink,
            topLeft = Offset(offsetPx, offsetPx),
            size = size,
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
    .background(containerColor, RoundedCornerShape(radius))
    .border(borderWidth, borderColor, RoundedCornerShape(radius))


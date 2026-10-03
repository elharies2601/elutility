package id.elharies.elutility.neobrutal.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalBorders
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalShadows
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalSpacing
import id.elharies.elutility.neobrutal.compose.foundation.brutalCard
import id.elharies.elutility.neobrutal.compose.theme.NeoBrutalTheme

/**
 * Neo-brutalist card container.
 *
 * @param modifier Modifier for the outer container.
 * @param containerColor Card background; defaults to surface when null.
 * @param radius Corner radius in dp.
 * @param borderWidth Border width in dp.
 * @param shadowOffset Hard offset for the shadow.
 * @param content Composable content inside the card.
 */
@Composable
fun NeoBrutalCard(
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    radius: Dp = 4.dp,
    borderWidth: Dp = NeoBrutalBorders.Default,
    shadowOffset: Dp = NeoBrutalShadows.Medium.offsetX,
    content: @Composable ColumnScope.() -> Unit
) {
    val brutal = LocalBrutalColors.current
    val surfaceColor = containerColor ?: brutal.surface

    Column(
        modifier = modifier
            .brutalCard(
                ink = brutal.ink,
                surface = surfaceColor,
                radius = radius,
                borderWidth = borderWidth,
                shadowOffset = shadowOffset
            )
            .padding(NeoBrutalSpacing.Xl),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun NeoBrutalCardPreview() {
    NeoBrutalTheme {
        NeoBrutalCard {
            Text(
                text = "Hello NeoUI",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(
                modifier = Modifier.height(NeoBrutalSpacing.Md)
            )
            Text(
                text = "This is a Neo-Brutalist card.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

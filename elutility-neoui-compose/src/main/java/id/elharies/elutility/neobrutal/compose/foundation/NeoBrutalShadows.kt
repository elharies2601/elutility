package id.elharies.elutility.neobrutal.compose.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NeoBrutalShadow(
    val offsetX: Dp,
    val offsetY: Dp
)

object NeoBrutalShadows {
    val Small = NeoBrutalShadow(
        offsetX = 2.dp,
        offsetY = 2.dp
    )

    val Medium = NeoBrutalShadow(
        offsetX = 4.dp,
        offsetY = 4.dp
    )

    val Large = NeoBrutalShadow(
        offsetX = 8.dp,
        offsetY = 8.dp
    )
}

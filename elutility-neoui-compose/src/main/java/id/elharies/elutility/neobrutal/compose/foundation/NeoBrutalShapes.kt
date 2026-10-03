package id.elharies.elutility.neobrutal.compose.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object NeoBrutalShapes {
    val ExtraSmall = RoundedCornerShape(4.dp)
    val Small = RoundedCornerShape(8.dp)
    val Medium = RoundedCornerShape(12.dp)
    val Large = RoundedCornerShape(16.dp)
    val ExtraLarge = RoundedCornerShape(20.dp)
}

val NeoBrutalShape = Shapes(
    extraSmall = NeoBrutalShapes.ExtraSmall,
    small = NeoBrutalShapes.Small,
    medium = NeoBrutalShapes.Medium,
    large = NeoBrutalShapes.Large,
    extraLarge = NeoBrutalShapes.ExtraLarge
)

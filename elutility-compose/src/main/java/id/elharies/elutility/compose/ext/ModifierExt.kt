package id.elharies.elutility.compose.ext

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.shimmerEffect(
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    ),
    animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = 1500,
            easing = LinearEasing,
            delayMillis = 0,
        ),
        repeatMode = RepeatMode.Restart
    ),
    angle: Float = 20f,
    gradientWidth: Float = 200f
): Modifier {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = gradientWidth * 3,
        animationSpec = animationSpec
    )

    return this.then(
        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnim.value - gradientWidth, translateAnim.value - gradientWidth),
                end = Offset(translateAnim.value, translateAnim.value)
            )
        )
    )
}
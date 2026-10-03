package id.elharies.elutility.neobrutal.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import id.elharies.elutility.neobrutal.compose.foundation.BrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.LocalBrutalColors
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalShape
import id.elharies.elutility.neobrutal.compose.foundation.NeoBrutalTypography

@Composable
fun NeoBrutalTheme(
    colors: BrutalColors = BrutalColors(),
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = colors.accent,
        onPrimary = colors.ink,
        secondary = colors.accentAlt,
        onSecondary = colors.ink,
        background = colors.background,
        onBackground = colors.ink,
        surface = colors.surface,
        onSurface = colors.ink,
        error = colors.danger,
        onError = colors.surface
    )

    CompositionLocalProvider(LocalBrutalColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = NeoBrutalShape,
            typography = NeoBrutalTypography,
            content = content
        )
    }
}

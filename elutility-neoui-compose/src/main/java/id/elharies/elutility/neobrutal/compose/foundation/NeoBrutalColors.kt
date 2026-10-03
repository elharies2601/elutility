package id.elharies.elutility.neobrutal.compose.foundation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class BrutalColors(
    val ink: Color = Color(0xFF111111),
    val surface: Color = Color(0xFFFFFFFF),
    val background: Color = Color(0xFFFFF8EE),
    val accent: Color = Color(0xFFFFD83D),
    val accentAlt: Color = Color(0xFF69D58C),
    val danger: Color = Color(0xFFFF5A5F),
    val mutedText: Color = Color(0xFF555555),
    val disabledText: Color = Color(0xFF999999),
    val disabledBackground: Color = Color(0xFFE8E8E8)
)

val LocalBrutalColors = staticCompositionLocalOf { BrutalColors() }

object NeoBrutalColors {
    val Ink = Color(0xFF111111)
    val White = Color(0xFFFFFFFF)
    val Background = Color(0xFFFFF8EE)
    val Surface = Color(0xFFFFFFFF)
    val Yellow = Color(0xFFFFD83D)
    val Pink = Color(0xFFFF6B9D)
    val Blue = Color(0xFF6C8CFF)
    val Green = Color(0xFF69D58C)
    val Purple = Color(0xFFB88CFF)
    val Orange = Color(0xFFFF9B54)
    val Success = Green
    val Error = Color(0xFFFF5A5F)
    val Warning = Orange
    val Info = Blue
    val TextPrimary = Ink
    val TextSecondary = Color(0xFF555555)
    val TextDisabled = Color(0xFF999999)
    val Border = Ink
    val DisabledBackground = Color(0xFFE8E8E8)
}

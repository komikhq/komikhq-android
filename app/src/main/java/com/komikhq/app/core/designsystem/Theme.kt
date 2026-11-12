package com.komikhq.app.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalIsDarkTheme = staticCompositionLocalOf { false }

@Immutable
data class HQColors(
    val background: Color,
    val surface: Color,
    val text: Color,
    val secondaryText: Color,
    val muted: Color,
    val border: Color,
    val primary: Color,
    val highlight: Color,
    val error: Color = ErrorRed,
    val success: Color = SuccessGreen,
) {
    val onBackground: Color get() = text
    val onSurface: Color get() = text
}

val LocalHQColors = staticCompositionLocalOf {
    HQColors(
        background = Color.White,
        surface = Stone100,
        text = Stone950,
        secondaryText = Stone500,
        muted = Stone400,
        border = BorderLight,
        primary = HQOrange,
        highlight = HQOrangeLight,
    )
}

val DarkHQColors = HQColors(
    background = Stone950,
    surface = Stone900,
    text = Stone50,
    secondaryText = Stone400,
    muted = Stone500,
    border = BorderDark,
    primary = HQOrange,
    highlight = HQOrangeMuted,
    error = ErrorRed,
)

val LightHQColors = HQColors(
    background = Color.White,
    surface = Stone100,
    text = Stone950,
    secondaryText = Stone500,
    muted = Stone400,
    border = BorderLight,
    primary = HQOrange,
    highlight = HQOrangeLight,
    error = ErrorRedDark,
)

object HQTheme {
    val colors: HQColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHQColors.current

    val typography: androidx.compose.material3.Typography
        get() = HQTypography
}

/**
 * 100% Custom KomikHQ Theme Provider without Material 3 container opinions or constraints.
 */
@Composable
fun KomikHQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val hqColors = if (darkTheme) DarkHQColors else LightHQColors

    CompositionLocalProvider(
        LocalIsDarkTheme provides darkTheme,
        LocalHQColors provides hqColors,
        content = content
    )
}

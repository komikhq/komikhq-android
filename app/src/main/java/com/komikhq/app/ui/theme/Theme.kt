package com.komikhq.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
)

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

private val DarkHQColors = HQColors(
    background = Stone950,
    surface = Stone900,
    text = Stone50,
    secondaryText = Stone400,
    muted = Stone500,
    border = BorderDark,
    primary = HQOrange,
    highlight = HQOrangeMuted,
)

private val LightHQColors = HQColors(
    background = Color.White,
    surface = Stone100,
    text = Stone950,
    secondaryText = Stone500,
    muted = Stone400,
    border = BorderLight,
    primary = HQOrange,
    highlight = HQOrangeLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = HQOrange,
    onPrimary = Stone50,
    primaryContainer = HQOrangeDark,
    onPrimaryContainer = Stone50,
    secondary = Stone800,
    onSecondary = Stone50,
    secondaryContainer = Stone800,
    onSecondaryContainer = Stone300,
    tertiary = HQOrange,
    onTertiary = Stone950,
    background = Stone950,
    onBackground = Stone50,
    surface = Stone900,
    onSurface = Stone50,
    surfaceVariant = Stone800,
    onSurfaceVariant = Stone400,
    outline = BorderDark,
    outlineVariant = BorderDark,
    error = ErrorRed,
    onError = Color.White,
    inverseSurface = Stone100,
    inverseOnSurface = Stone950,
)

private val LightColorScheme = lightColorScheme(
    primary = HQOrange,
    onPrimary = Color.White,
    primaryContainer = HQOrangeLight,
    onPrimaryContainer = HQOrangeDark,
    secondary = Stone200,
    onSecondary = Stone950,
    secondaryContainer = Stone100,
    onSecondaryContainer = Stone800,
    tertiary = HQOrange,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Stone950,
    surface = Stone100,
    onSurface = Stone950,
    surfaceVariant = Stone200,
    onSurfaceVariant = Stone500,
    outline = BorderLight,
    outlineVariant = BorderLight,
    error = ErrorRedDark,
    onError = Color.White,
    inverseSurface = Stone900,
    inverseOnSurface = Stone50,
)

object HQTheme {
    val colors: HQColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHQColors.current
}

@Composable
fun KomikHQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val hqColors = if (darkTheme) DarkHQColors else LightHQColors

    CompositionLocalProvider(
        LocalIsDarkTheme provides darkTheme,
        LocalHQColors provides hqColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

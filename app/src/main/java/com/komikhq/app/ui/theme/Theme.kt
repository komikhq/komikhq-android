package com.komikhq.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalIsDarkTheme = staticCompositionLocalOf { false }

private val DarkColorScheme = darkColorScheme(
    primary = Orange600,
    onPrimary = Stone50,
    primaryContainer = Orange700,
    onPrimaryContainer = Stone50,
    secondary = Stone800,
    onSecondary = Stone50,
    secondaryContainer = Stone800,
    onSecondaryContainer = Stone300,
    tertiary = Orange500,
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
    primary = Orange600,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDD5),
    onPrimaryContainer = Orange700,
    secondary = Stone200,
    onSecondary = Stone950,
    secondaryContainer = Stone100,
    onSecondaryContainer = Stone800,
    tertiary = Orange500,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Stone950,
    surface = Color.White,
    onSurface = Stone950,
    surfaceVariant = Stone100,
    onSurfaceVariant = Stone500,
    outline = BorderLight,
    outlineVariant = BorderLight,
    error = ErrorRedDark,
    onError = Color.White,
    inverseSurface = Stone900,
    inverseOnSurface = Stone50,
)

@Composable
fun KomikHQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

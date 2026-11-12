package com.komikhq.app.ui.theme

// Re-export designsystem tokens for backward compatibility
typealias HQColors = com.komikhq.app.core.designsystem.HQColors
val LocalHQColors = com.komikhq.app.core.designsystem.LocalHQColors
val LocalIsDarkTheme = com.komikhq.app.core.designsystem.LocalIsDarkTheme
val HQTheme = com.komikhq.app.core.designsystem.HQTheme

@androidx.compose.runtime.Composable
fun KomikHQTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @androidx.compose.runtime.Composable () -> Unit
) {
    com.komikhq.app.core.designsystem.KomikHQTheme(darkTheme = darkTheme, content = content)
}

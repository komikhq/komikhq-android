package com.komikhq.app.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

typealias LogoVariant = com.komikhq.app.core.ui.LogoVariant
typealias LogoSize = com.komikhq.app.core.ui.LogoSize

@Composable
fun KomikHQLogo(
    modifier: Modifier = Modifier,
    variant: com.komikhq.app.core.ui.LogoVariant = com.komikhq.app.core.ui.LogoVariant.FULL,
    size: com.komikhq.app.core.ui.LogoSize = com.komikhq.app.core.ui.LogoSize.HEADER,
    isDark: Boolean = com.komikhq.app.core.designsystem.LocalIsDarkTheme.current
) {
    com.komikhq.app.core.ui.HQLogo(
        modifier = modifier,
        variant = variant,
        size = size,
        isDark = isDark
    )
}

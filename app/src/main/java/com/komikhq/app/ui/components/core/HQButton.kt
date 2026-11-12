package com.komikhq.app.ui.components.core

typealias HQButtonVariant = com.komikhq.app.core.ui.HQButtonVariant
typealias HQButtonSize = com.komikhq.app.core.ui.HQButtonSize

@androidx.compose.runtime.Composable
fun HQButton(
    text: String,
    onClick: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    variant: com.komikhq.app.core.ui.HQButtonVariant = com.komikhq.app.core.ui.HQButtonVariant.PRIMARY,
    size: com.komikhq.app.core.ui.HQButtonSize = com.komikhq.app.core.ui.HQButtonSize.MD,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    fullWidth: Boolean = true,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    com.komikhq.app.core.ui.HQButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        enabled = enabled,
        isLoading = isLoading,
        fullWidth = fullWidth,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

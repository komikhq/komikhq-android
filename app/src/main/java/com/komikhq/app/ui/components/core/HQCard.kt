package com.komikhq.app.ui.components.core

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HQCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = com.komikhq.app.core.designsystem.HQTheme.colors.surface,
    borderColor: Color = com.komikhq.app.core.designsystem.HQTheme.colors.border,
    cornerRadius: Dp = 16.dp,
    padding: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    com.komikhq.app.core.ui.HQCard(
        modifier = modifier,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        cornerRadius = cornerRadius,
        padding = padding,
        onClick = onClick,
        content = content
    )
}

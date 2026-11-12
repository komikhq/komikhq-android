package com.komikhq.app.ui.components.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HQAvatar(
    avatarUrl: String? = null,
    displayName: String? = null,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    onClick: (() -> Unit)? = null,
    imageUrl: String? = null,
    name: String? = null
) {
    com.komikhq.app.core.ui.HQAvatar(
        avatarUrl = avatarUrl,
        displayName = displayName,
        modifier = modifier,
        size = size,
        onClick = onClick,
        imageUrl = imageUrl,
        name = name
    )
}

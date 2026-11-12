package com.komikhq.app.ui.components.core

@androidx.compose.runtime.Composable
fun HQFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    com.komikhq.app.core.ui.HQFilterChip(
        label = label,
        selected = selected,
        onClick = onClick,
        modifier = modifier
    )
}

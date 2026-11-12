package com.komikhq.app.ui.components.feedback

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.komikhq.app.core.ui.HQEmptyState

@Composable
fun EmptyContentState(
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    HQEmptyState(
        title = title,
        description = message,
        modifier = modifier
    )
}

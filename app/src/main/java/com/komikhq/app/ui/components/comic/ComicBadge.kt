package com.komikhq.app.ui.components.comic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.komikhq.app.ui.components.core.HQBadge
import com.komikhq.app.ui.components.core.HQBadgeVariant

// ============================================================
// Domain-specific wrappers built on top of HQBadge primitives
// ============================================================

@Composable
fun ComicTypeBadge(
    type: String,
    modifier: Modifier = Modifier
) {
    HQBadge(
        text = type.uppercase(),
        modifier = modifier,
        variant = HQBadgeVariant.SOLID_PRIMARY
    )
}

@Composable
fun ComicChapterBadge(
    totalChapters: Int,
    modifier: Modifier = Modifier
) {
    HQBadge(
        text = "$totalChapters Ch.",
        modifier = modifier,
        variant = HQBadgeVariant.TRANSLUCENT
    )
}

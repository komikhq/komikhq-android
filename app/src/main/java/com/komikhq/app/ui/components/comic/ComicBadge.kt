package com.komikhq.app.ui.components.comic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.komikhq.app.R
import com.komikhq.app.ui.components.core.HQBadge
import com.komikhq.app.ui.components.core.HQBadgeVariant

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
        text = stringResource(R.string.detail_chapters_count, totalChapters),
        modifier = modifier,
        variant = HQBadgeVariant.TRANSLUCENT
    )
}

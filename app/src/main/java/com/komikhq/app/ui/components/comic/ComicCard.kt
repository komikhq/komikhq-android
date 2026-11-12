package com.komikhq.app.ui.components.comic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ComicCard(
    title: String,
    coverUrl: String,
    score: Double? = null,
    status: String? = null,
    latestChapter: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 152.dp
) {
    com.komikhq.app.core.ui.ComicCard(
        title = title,
        coverUrl = coverUrl,
        score = score,
        status = status,
        latestChapter = latestChapter,
        onClick = onClick,
        modifier = modifier,
        cardWidth = cardWidth
    )
}

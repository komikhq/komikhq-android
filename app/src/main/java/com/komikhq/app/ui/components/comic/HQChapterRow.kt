package com.komikhq.app.ui.components.comic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HQChapterRow(
    chapterNumber: String,
    title: String,
    date: String,
    isRead: Boolean = false,
    isDownloaded: Boolean = false,
    onClick: () -> Unit,
    onDownloadClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    com.komikhq.app.core.ui.HQChapterRow(
        chapterNumber = chapterNumber,
        title = title,
        date = date,
        isRead = isRead,
        isDownloaded = isDownloaded,
        onClick = onClick,
        onDownloadClick = onDownloadClick,
        modifier = modifier
    )
}

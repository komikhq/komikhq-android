package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

/**
 * 68dp Chapter item matching Figma Comic Detail & Reader specifications.
 * Zero dependency on Material 3.
 */
@Composable
fun HQChapterRow(
    chapterNumber: String = "",
    title: String = "",
    date: String = "",
    subtitle: String = "",
    isRead: Boolean = false,
    isDownloaded: Boolean = false,
    onClick: () -> Unit = {},
    onDownloadClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val displayDate = if (date.isNotBlank()) date else subtitle
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isRead) HQTheme.colors.surface.copy(alpha = 0.5f) else HQTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Read indicator dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isRead) HQTheme.colors.muted else HQOrange)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    BasicText(
                        text = if (title.isNotBlank()) stringResource(R.string.reader_chapter_format, chapterNumber) + " - " + title
                               else stringResource(R.string.reader_chapter_format, chapterNumber),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = if (isRead) FontWeight.Normal else FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = if (isRead) HQTheme.colors.secondaryText else HQTheme.colors.text
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    BasicText(
                        text = displayDate,
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = HQTheme.colors.muted
                        )
                    )
                }
            }

            if (onDownloadClick != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onDownloadClick),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = if (isDownloaded) HQIcons.DownloadDone else HQIcons.Download,
                        contentDescription = stringResource(if (isDownloaded) R.string.action_downloaded else R.string.action_download),
                        tint = if (isDownloaded) HQOrange else HQTheme.colors.secondaryText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

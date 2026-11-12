package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.designsystem.Stone950

/**
 * 2:3 aspect ratio Comic Card matching Figma Home and Explore specifications.
 * Zero dependency on Material 3.
 */
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
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = modifier
            .width(cardWidth)
            .clickable(onClick = onClick)
    ) {
        // Poster with 2:3 aspect ratio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(shape)
                .background(HQTheme.colors.surface)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Stone950.copy(alpha = 0.85f)
                            ),
                            startY = 180f
                        )
                    )
            )

            // Top Badges
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (status != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Stone950.copy(alpha = 0.75f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        BasicText(
                            text = if (status.equals("completed", ignoreCase = true)) {
                                stringResource(R.string.detail_status_completed)
                            } else {
                                stringResource(R.string.detail_status_ongoing)
                            },
                            style = TextStyle(
                                fontFamily = InterFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        )
                    }
                }

                if (score != null && score > 0) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Stone950.copy(alpha = 0.75f))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HQIcon(
                            imageVector = HQIcons.Star,
                            contentDescription = null,
                            tint = HQOrange,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        BasicText(
                            text = String.format("%.1f", score),
                            style = TextStyle(
                                fontFamily = InterFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }

            // Latest chapter pill at bottom
            if (latestChapter != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(HQOrange)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    BasicText(
                        text = stringResource(R.string.reader_chapter_format, latestChapter),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        BasicText(
            text = title,
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = HQTheme.colors.text
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

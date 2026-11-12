package com.komikhq.app.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQChapterRow
import com.komikhq.app.core.ui.HQCircularProgressIndicator
import com.komikhq.app.core.ui.HQEmptyState

/**
 * KHQ 55 · Comic Detail Screen (Figma 2:33)
 * 110×156dp cover, dual CTAs, expandable synopsis, chapter list
 */
@Composable
fun ComicDetailScreen(
    viewModel: ComicDetailViewModel,
    onNavigateBack: () -> Unit,
    onReadChapter: (comicSlug: String, chapterNumber: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HQCircularProgressIndicator(color = HQOrange, size = 40.dp, strokeWidth = 3.dp)
                }
            }
            is DetailUiState.Error -> {
                HQEmptyState(
                    title = stringResource(R.string.error_boundary_title),
                    description = state.message,
                    actionLabel = stringResource(R.string.action_retry),
                    onActionClick = { viewModel.loadDetail() },
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailUiState.Success -> {
                DetailContent(
                    comic = state.comic,
                    chapters = state.chapters,
                    isBookmarked = state.isBookmarked,
                    onNavigateBack = onNavigateBack,
                    onReadChapter = onReadChapter,
                    onToggleBookmark = viewModel::toggleBookmark
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    comic: ComicDetail,
    chapters: List<ChapterItem>,
    isBookmarked: Boolean,
    onNavigateBack: () -> Unit,
    onReadChapter: (String, String) -> Unit,
    onToggleBookmark: () -> Unit
) {
    var synopsisExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero banner with cover
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                // Background blur/cover
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(comic.coverUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    HQTheme.colors.background.copy(alpha = 0.3f),
                                    HQTheme.colors.background
                                )
                            )
                        )
                )

                // Back button
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Stone950.copy(alpha = 0.5f))
                        .clickable(onClick = onNavigateBack),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.ArrowBack,
                        contentDescription = stringResource(R.string.action_back),
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Cover + info row
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    // Cover 110×156dp
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(comic.coverUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = comic.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(110.dp)
                            .aspectRatio(110f / 156f)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        BasicText(
                            text = comic.title,
                            style = TextStyle(
                                fontFamily = InstrumentSansFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                lineHeight = 26.sp,
                                color = HQTheme.colors.text
                            ),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        if (comic.author != null) {
                            BasicText(
                                text = comic.author,
                                style = TextStyle(
                                    fontFamily = InterFamily,
                                    fontSize = 13.sp,
                                    color = HQTheme.colors.secondaryText
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Status + Rating
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (comic.status != null) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(HQOrange.copy(alpha = 0.12f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    BasicText(
                                        text = if (comic.status.equals("completed", true))
                                            stringResource(R.string.detail_status_completed)
                                        else stringResource(R.string.detail_status_ongoing),
                                        style = TextStyle(
                                            fontFamily = InterFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.sp,
                                            color = HQOrange
                                        )
                                    )
                                }
                            }

                            if (comic.rating != null && comic.rating > 0) {
                                Spacer(modifier = Modifier.width(8.dp))
                                HQIcon(
                                    imageVector = HQIcons.Star,
                                    contentDescription = null,
                                    tint = HQOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                BasicText(
                                    text = String.format("%.1f", comic.rating),
                                    style = TextStyle(
                                        fontFamily = InterFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        color = HQTheme.colors.text
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Dual CTA buttons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HQButton(
                    text = if (chapters.isNotEmpty())
                        stringResource(R.string.detail_action_read_first)
                    else stringResource(R.string.detail_action_read_first),
                    onClick = {
                        chapters.lastOrNull()?.let { ch ->
                            onReadChapter(comic.slug, ch.number)
                        }
                    },
                    size = HQButtonSize.LG,
                    modifier = Modifier.weight(1f)
                )
                HQButton(
                    text = if (isBookmarked)
                        stringResource(R.string.detail_action_bookmarked)
                    else stringResource(R.string.detail_action_bookmark),
                    onClick = onToggleBookmark,
                    variant = HQButtonVariant.OUTLINE,
                    size = HQButtonSize.LG,
                    leadingIcon = if (isBookmarked) HQIcons.BookmarkFilled else HQIcons.Bookmark,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Genres
        if (comic.genres.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    comic.genres.take(4).forEach { genre ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(HQTheme.colors.surface)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            BasicText(
                                text = genre,
                                style = TextStyle(
                                    fontFamily = InterFamily,
                                    fontSize = 12.sp,
                                    color = HQTheme.colors.secondaryText
                                )
                            )
                        }
                    }
                }
            }
        }

        // Synopsis
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                BasicText(
                    text = stringResource(R.string.detail_synopsis_title),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = HQTheme.colors.text
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                BasicText(
                    text = comic.synopsis,
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = HQTheme.colors.secondaryText
                    ),
                    maxLines = if (synopsisExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis
                )
                if (comic.synopsis.length > 120) {
                    Spacer(modifier = Modifier.height(6.dp))
                    BasicText(
                        text = if (synopsisExpanded)
                            stringResource(R.string.detail_synopsis_less)
                        else stringResource(R.string.detail_synopsis_more),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = HQOrange
                        ),
                        modifier = Modifier.clickable { synopsisExpanded = !synopsisExpanded }
                    )
                }
            }
        }

        // Chapter List Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = stringResource(R.string.detail_chapters_title),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = HQTheme.colors.text
                    )
                )
                BasicText(
                    text = stringResource(R.string.detail_chapters_count, chapters.size),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 13.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )
            }
        }

        // Chapter items
        items(chapters, key = { it.id }) { chapter ->
            HQChapterRow(
                chapterNumber = chapter.number,
                title = chapter.title,
                date = chapter.createdAt ?: "",
                isRead = chapter.isRead,
                isDownloaded = chapter.isDownloaded,
                onClick = { onReadChapter(comic.slug, chapter.number) },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}


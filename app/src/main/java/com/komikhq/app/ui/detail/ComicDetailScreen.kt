package com.komikhq.app.ui.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.komikhq.app.R
import com.komikhq.app.ui.components.comic.ComicTypeBadge
import com.komikhq.app.ui.components.comic.HQChapterRow
import com.komikhq.app.ui.components.core.HQButton
import com.komikhq.app.ui.components.core.HQButtonVariant
import com.komikhq.app.ui.theme.HQTheme
import com.komikhq.app.ui.theme.InstrumentSansFamily
import com.komikhq.app.ui.theme.InterFamily

@Composable
fun ComicDetailScreen(
    onBackClick: () -> Unit,
    onChapterClick: (comicSlug: String, chapterSlug: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ComicDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // App Bar (64 dp) - Figma node 2:35
        DetailTopAppBar(onBackClick = onBackClick)

        when (val state = uiState) {
            is ComicDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is ComicDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            fontFamily = InterFamily,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HQButton(
                            text = stringResource(R.string.action_retry),
                            onClick = { viewModel.loadComicDetail() }
                        )
                    }
                }
            }
            is ComicDetailUiState.Success -> {
                ComicDetailContent(
                    detail = state.detail,
                    onToggleBookmark = viewModel::toggleBookmark,
                    onChapterClick = onChapterClick
                )
            }
        }
    }
}

@Composable
private fun DetailTopAppBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
                tint = HQTheme.colors.text,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = stringResource(R.string.detail_title),
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = HQTheme.colors.text
        )

        IconButton(onClick = { /* More actions */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.action_more),
                tint = HQTheme.colors.text,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ComicDetailContent(
    detail: ComicDetail,
    onToggleBookmark: () -> Unit,
    onChapterClick: (comicSlug: String, chapterSlug: String) -> Unit
) {
    val comic = detail.comic

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 32.dp)
    ) {
        item {
            // Hero info section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                // Cover Image (110 x 156 dp)
                AsyncImage(
                    model = comic.coverUrl,
                    contentDescription = comic.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(110.dp)
                        .aspectRatio(0.7f)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    if (!comic.type.isNullOrBlank()) {
                        ComicTypeBadge(type = comic.type)
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Text(
                        text = comic.title,
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        color = HQTheme.colors.text,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!detail.author.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = detail.author,
                            fontFamily = InterFamily,
                            fontSize = 13.sp,
                            color = HQTheme.colors.secondaryText
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.detail_chapters_count, detail.chapters.size),
                        fontFamily = InterFamily,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item {
            // Action Buttons (Start Reading & Bookmark)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val firstChapter = detail.chapters.firstOrNull()
                HQButton(
                    text = stringResource(R.string.detail_action_read_first),
                    onClick = {
                        firstChapter?.let { ch ->
                            onChapterClick(comic.slug, ch.slug)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    variant = HQButtonVariant.PRIMARY
                )

                HQButton(
                    text = stringResource(
                        if (detail.isBookmarked) R.string.detail_action_bookmarked
                        else R.string.detail_action_bookmark
                    ),
                    onClick = onToggleBookmark,
                    modifier = Modifier.weight(1f),
                    variant = HQButtonVariant.OUTLINE,
                    leadingIcon = if (detail.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder
                )
            }
        }

        if (!detail.description.isNullOrBlank()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                    Text(
                        text = stringResource(R.string.detail_synopsis_title),
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = HQTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = detail.description,
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = HQTheme.colors.secondaryText
                    )
                }
            }
        }

        if (comic.genres.isNotEmpty()) {
            item {
                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(comic.genres) { genre ->
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = HQTheme.colors.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, HQTheme.colors.border)
                        ) {
                            Text(
                                text = genre,
                                fontFamily = InterFamily,
                                fontSize = 12.sp,
                                color = HQTheme.colors.secondaryText,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            // Chapter list header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.detail_chapters_title),
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = HQTheme.colors.text
                )
                Text(
                    text = stringResource(R.string.detail_chapters_count, detail.chapters.size),
                    fontFamily = InterFamily,
                    fontSize = 13.sp,
                    color = HQTheme.colors.muted
                )
            }
        }

        // Chapter rows
        items(detail.chapters, key = { it.id }) { chapter ->
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                HQChapterRow(
                    chapterTitle = chapter.title,
                    releaseDate = chapter.releaseDate,
                    isRead = chapter.isRead,
                    onClick = { onChapterClick(comic.slug, chapter.slug) },
                    onDownloadClick = { /* Handle offline download */ }
                )
            }
        }
    }
}


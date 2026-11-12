package com.komikhq.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.komikhq.app.data.model.ComicItem
import com.komikhq.app.ui.components.comic.ComicCard
import com.komikhq.app.ui.components.core.HQButton
import com.komikhq.app.ui.theme.HQOrange
import com.komikhq.app.ui.theme.HQTheme
import com.komikhq.app.ui.theme.InstrumentSansFamily
import com.komikhq.app.ui.theme.InterFamily

@Composable
fun HomeScreen(
    onComicClick: (slug: String) -> Unit,
    onContinueReadingClick: (comicSlug: String, chapterSlug: String) -> Unit,
    onHistoryClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // App Bar (64 dp) - Figma node 2:25
        HomeTopAppBar(onNotificationsClick = onNotificationsClick)

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is HomeUiState.Error -> {
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
                            onClick = { viewModel.loadHomeFeed() }
                        )
                    }
                }
            }
            is HomeUiState.Success -> {
                HomeContent(
                    feed = state.feed,
                    onComicClick = onComicClick,
                    onContinueReadingClick = onContinueReadingClick,
                    onHistoryClick = onHistoryClick
                )
            }
        }
    }
}

@Composable
private fun HomeTopAppBar(
    onNotificationsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo composite: "komik" + "hq"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.brand_komik),
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = HQTheme.colors.text
            )
            Text(
                text = stringResource(R.string.brand_hq),
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = HQOrange
            )
        }

        IconButton(onClick = onNotificationsClick) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = stringResource(R.string.home_notifications),
                tint = HQTheme.colors.text,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun HomeContent(
    feed: HomeFeed,
    onComicClick: (slug: String) -> Unit,
    onContinueReadingClick: (comicSlug: String, chapterSlug: String) -> Unit,
    onHistoryClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        // Editorial Greeting
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)) {
            Text(
                text = stringResource(R.string.home_hero_title),
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                lineHeight = 32.sp,
                color = HQTheme.colors.text
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.home_hero_subtitle),
                fontFamily = InterFamily,
                fontSize = 14.sp,
                color = HQTheme.colors.secondaryText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Comic Hero Card (Figma node 2:380)
        feed.featuredComics.firstOrNull()?.let { featured ->
            FeaturedHeroCard(
                comic = featured,
                onClick = { onComicClick(featured.slug) }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Carousel dots
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 18.dp, height = 5.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(6.dp))
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(width = 6.dp, height = 5.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(HQTheme.colors.border)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }

        // Continue Reading Section (Figma node 2:397)
        feed.continueReading?.let { history ->
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_continue_reading_title),
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text
                )
                Text(
                    text = stringResource(R.string.home_continue_reading_history),
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable(onClick = onHistoryClick)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            ContinueReadingCard(
                history = history,
                onClick = { onContinueReadingClick(history.comicSlug, history.chapterSlug) }
            )
        }

        // Latest Releases Section
        if (feed.latestComics.isNotEmpty()) {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(title = stringResource(R.string.home_latest_releases_title))
            Spacer(modifier = Modifier.height(14.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(feed.latestComics, key = { it.id }) { comic ->
                    Box(modifier = Modifier.width(148.dp)) {
                        ComicCard(
                            comic = comic,
                            onClick = { onComicClick(comic.slug) }
                        )
                    }
                }
            }
        }

        // Popular Section
        if (feed.popularComics.isNotEmpty()) {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(title = stringResource(R.string.home_popular_title))
            Spacer(modifier = Modifier.height(14.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(feed.popularComics, key = { it.id }) { comic ->
                    Box(modifier = Modifier.width(148.dp)) {
                        ComicCard(
                            comic = comic,
                            onClick = { onComicClick(comic.slug) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = InstrumentSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = HQTheme.colors.text,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
private fun FeaturedHeroCard(
    comic: ComicItem,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(248.dp)
            .padding(horizontal = 24.dp)
            .clip(shape)
            .border(1.dp, HQTheme.colors.border, shape)
            .clickable(onClick = onClick)
    ) {
        // Artwork Background
        AsyncImage(
            model = comic.coverUrl,
            contentDescription = comic.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Readability Dark Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        // Content Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Label Pill (Top Left)
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_featured_badge),
                    color = Color.White,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            // Bottom Title & Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                    Text(
                        text = comic.title,
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        lineHeight = 26.sp,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (comic.genres.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = comic.genres.joinToString(" · "),
                            fontFamily = InterFamily,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Circle Read Action Button (44x44 dp)
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = stringResource(R.string.home_action_read_featured),
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContinueReadingCard(
    history: ContinueReadingItem,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(14.dp)

    Surface(
        shape = shape,
        color = HQTheme.colors.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, HQTheme.colors.border),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 24.dp)
            .clip(shape)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cover thumbnail (57x80 dp)
            AsyncImage(
                model = history.coverUrl,
                contentDescription = history.comicTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 57.dp, height = 80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = history.comicTitle,
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = HQTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${history.chapterTitle} · " + stringResource(
                        R.string.reader_page_progress,
                        history.currentPage,
                        history.totalPages
                    ),
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = HQTheme.colors.secondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                val progress = if (history.totalPages > 0) {
                    (history.currentPage.toFloat() / history.totalPages.toFloat()).coerceIn(0f, 1f)
                } else 0f
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = HQTheme.colors.border
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Play Icon Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

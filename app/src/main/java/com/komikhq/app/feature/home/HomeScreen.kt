package com.komikhq.app.feature.home

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.komikhq.app.core.ui.ComicCard
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQCircularProgressIndicator
import com.komikhq.app.core.ui.HQLogo
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant
import com.komikhq.app.data.model.ComicItem

/**
 * KHQ 15 · Home Screen (Figma 2:23)
 * Hero card 364×248dp, continue reading card 364×100dp, LazyRow carousels
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onComicClick: (String) -> Unit,
    onContinueReading: (comicSlug: String, chapterSlug: String) -> Unit,
    onNotificationsClick: () -> Unit,
    onViewAllLatest: () -> Unit,
    onViewAllPopular: () -> Unit,
    onProfileClick: () -> Unit = {},
    isGuest: Boolean = false,
    onGuestSignIn: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
    ) {
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                HomeSkeletonContent()
            }
            is HomeUiState.Error -> {
                HomeErrorContent(
                    message = state.message,
                    onRetry = { viewModel.loadHomeFeed() }
                )
            }
            is HomeUiState.Success -> {
                HomeSuccessContent(
                    feed = state.feed,
                    onComicClick = onComicClick,
                    onContinueReading = onContinueReading,
                    onNotificationsClick = onNotificationsClick,
                    onViewAllLatest = onViewAllLatest,
                    onViewAllPopular = onViewAllPopular,
                    onProfileClick = onProfileClick,
                    isGuest = isGuest,
                    onGuestSignIn = onGuestSignIn
                )
            }
        }
    }
}

@Composable
private fun HomeSuccessContent(
    feed: HomeFeed,
    onComicClick: (String) -> Unit,
    onContinueReading: (String, String) -> Unit,
    onNotificationsClick: () -> Unit,
    onViewAllLatest: () -> Unit,
    onViewAllPopular: () -> Unit,
    onProfileClick: () -> Unit,
    isGuest: Boolean,
    onGuestSignIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HQLogo(variant = LogoVariant.FULL, size = LogoSize.HEADER)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Notifications icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(HQTheme.colors.surface)
                        .clickable(onClick = onNotificationsClick),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.Bell,
                        contentDescription = stringResource(R.string.home_notifications),
                        tint = HQTheme.colors.text,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Profile / Account icon (KHQ 21 / KHQ 22)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(HQTheme.colors.surface)
                        .clickable(onClick = onProfileClick),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.User,
                        contentDescription = stringResource(R.string.profile_title),
                        tint = HQTheme.colors.text,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Guest Banner (KHQ 59)
        if (isGuest) {
            HomeGuestBanner(onSignIn = onGuestSignIn)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Hero Featured Card (364×248dp)
        if (feed.featuredComics.isNotEmpty()) {
            val heroComic = feed.featuredComics.first()
            HeroCard(
                comic = heroComic,
                onClick = { onComicClick(heroComic.slug) },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Continue Reading Card (364×100dp)
        if (feed.continueReading != null) {
            ContinueReadingCard(
                item = feed.continueReading,
                onClick = { onContinueReading(feed.continueReading.comicSlug, feed.continueReading.chapterSlug) },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Latest Releases
        if (feed.latestComics.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.home_latest_releases_title),
                onViewAll = onViewAllLatest
            )
            Spacer(modifier = Modifier.height(12.dp))
            ComicCarousel(
                comics = feed.latestComics,
                onComicClick = onComicClick
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Popular
        if (feed.popularComics.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.home_popular_title),
                onViewAll = onViewAllPopular
            )
            Spacer(modifier = Modifier.height(12.dp))
            ComicCarousel(
                comics = feed.popularComics,
                onComicClick = onComicClick
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Trending
        if (feed.featuredComics.size > 1) {
            SectionHeader(
                title = stringResource(R.string.home_trending_title),
                onViewAll = null
            )
            Spacer(modifier = Modifier.height(12.dp))
            ComicCarousel(
                comics = feed.featuredComics.drop(1),
                onComicClick = onComicClick
            )
        }

        Spacer(modifier = Modifier.height(100.dp)) // bottom nav spacing
    }
}

@Composable
private fun HeroCard(
    comic: ComicItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(248.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(HQTheme.colors.surface)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(comic.coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = comic.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Stone950.copy(alpha = 0.9f)
                        ),
                        startY = 100f
                    )
                )
        )

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        ) {
            if (comic.type != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(HQOrange)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    BasicText(
                        text = comic.type,
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            BasicText(
                text = comic.title,
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    color = Color.White
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (comic.genres.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                BasicText(
                    text = comic.genres.take(3).joinToString(" · "),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}

@Composable
private fun ContinueReadingCard(
    item: ContinueReadingItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cover thumbnail
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = item.comicTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(52.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = stringResource(R.string.home_continue_reading_title),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = HQOrange,
                    letterSpacing = 0.5.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicText(
                text = item.comicTitle,
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = HQTheme.colors.text
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            BasicText(
                text = item.chapterTitle,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = HQTheme.colors.secondaryText
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        HQIcon(
            imageVector = HQIcons.ChevronRight,
            contentDescription = null,
            tint = HQTheme.colors.muted,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    onViewAll: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = title,
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HQTheme.colors.text
            )
        )
        if (onViewAll != null) {
            BasicText(
                text = stringResource(R.string.action_view_all),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = HQOrange
                ),
                modifier = Modifier.clickable(onClick = onViewAll)
            )
        }
    }
}

@Composable
private fun ComicCarousel(
    comics: List<ComicItem>,
    onComicClick: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(comics, key = { it.id }) { comic ->
            ComicCard(
                title = comic.title,
                coverUrl = comic.coverUrl ?: "",
                status = comic.status,
                onClick = { onComicClick(comic.slug) },
                cardWidth = 140.dp
            )
        }
    }
}

@Composable
fun HomeGuestBanner(
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HQOrange.copy(alpha = 0.08f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = stringResource(R.string.home_guest_banner_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = HQTheme.colors.text
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicText(
                text = stringResource(R.string.home_guest_banner_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = HQTheme.colors.secondaryText
                )
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        HQButton(
            text = stringResource(R.string.home_guest_banner_cta),
            onClick = onSignIn,
            variant = HQButtonVariant.PRIMARY,
            fullWidth = false
        )
    }
}

@Composable
fun HomeSkeletonContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HQCircularProgressIndicator(
            color = HQOrange,
            size = 40.dp,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun HomeErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicText(
            text = stringResource(R.string.error_boundary_title),
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HQTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicText(
            text = message,
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 14.sp,
                color = HQTheme.colors.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        HQButton(
            text = stringResource(R.string.action_retry),
            onClick = onRetry,
            fullWidth = false
        )
    }
}


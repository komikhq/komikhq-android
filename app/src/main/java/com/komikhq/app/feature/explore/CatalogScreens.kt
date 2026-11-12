package com.komikhq.app.feature.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.ui.ComicCard
import com.komikhq.app.core.ui.HQCircularProgressIndicator
import com.komikhq.app.core.ui.HQEmptyState
import com.komikhq.app.core.ui.HQText

/**
 * KHQ 60 — Catalog Trending screen.
 * Shows comics filtered by trending (daily/weekly/monthly popularity).
 */
@Composable
fun CatalogTrendingScreen(
    viewModel: ExploreViewModel,
    onComicClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    CatalogListScreen(
        title = stringResource(R.string.catalog_trending_title),
        viewModel = viewModel,
        sortBy = "trending",
        onComicClick = onComicClick,
        onNavigateBack = onNavigateBack
    )
}

/**
 * KHQ 61 — Catalog Popular screen.
 * Shows all-time most viewed comics.
 */
@Composable
fun CatalogPopularScreen(
    viewModel: ExploreViewModel,
    onComicClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    CatalogListScreen(
        title = stringResource(R.string.catalog_popular_title),
        viewModel = viewModel,
        sortBy = "popular",
        onComicClick = onComicClick,
        onNavigateBack = onNavigateBack
    )
}

/**
 * KHQ 62 — Catalog Completed (empty filter result view).
 */
@Composable
fun CatalogCompletedScreen(
    viewModel: ExploreViewModel,
    onComicClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    CatalogListScreen(
        title = stringResource(R.string.catalog_completed_title),
        viewModel = viewModel,
        sortBy = "completed",
        onComicClick = onComicClick,
        onNavigateBack = onNavigateBack
    )
}

/**
 * Shared catalog list layout used by Trending, Popular, Completed screens.
 */
@Composable
private fun CatalogListScreen(
    title: String,
    viewModel: ExploreViewModel,
    sortBy: String,
    onComicClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(sortBy) {
        viewModel.setSortBy(sortBy)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = onNavigateBack),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.ArrowBack,
                    contentDescription = null,
                    tint = HQTheme.colors.text
                )
            }
            // Title
            HQText(
                text = title,
                style = HQTheme.typography.titleMedium,
                color = HQTheme.colors.text,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Content
        when (val state = uiState) {
            is ExploreUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    HQCircularProgressIndicator()
                }
            }
            is ExploreUiState.Error -> {
                HQEmptyState(
                    title = stringResource(R.string.empty_catalog_title),
                    description = state.message,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is ExploreUiState.Success -> {
                if (state.data.comics.isEmpty()) {
                    HQEmptyState(
                        title = stringResource(R.string.empty_catalog_title),
                        description = stringResource(R.string.empty_catalog_message),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.data.comics) { comic ->
                            ComicCard(
                                title = comic.title,
                                coverUrl = comic.coverUrl ?: "",
                                onClick = { onComicClick(comic.slug) }
                            )
                        }
                    }
                }
            }
        }
    }
}

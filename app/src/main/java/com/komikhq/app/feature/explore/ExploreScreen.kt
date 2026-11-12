package com.komikhq.app.feature.explore

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.ComicCard
import com.komikhq.app.core.ui.HQCircularProgressIndicator
import com.komikhq.app.core.ui.HQEmptyState
import com.komikhq.app.core.ui.HQFilterChip
import com.komikhq.app.core.ui.HQSearchField

/**
 * KHQ 16 · Explore / Discover Catalog Screen (Figma 2:28)
 * Covers KHQ 16 (catalog), 17 (search suggestions), 18 (search results),
 * 20 (filtered catalog), 53 (search empty), 60-62 (trending/popular/completed empty)
 */
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel,
    onComicClick: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HQIcon(
                        imageVector = HQIcons.Compass,
                        contentDescription = null,
                        tint = HQOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicText(
                        text = stringResource(R.string.explore_title),
                        style = TextStyle(
                            fontFamily = InstrumentSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = HQTheme.colors.text
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onFilterClick),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.Filter,
                        contentDescription = stringResource(R.string.action_filter),
                        tint = HQTheme.colors.text,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Search Bar
            HQSearchField(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearch = { viewModel.onSearch() },
                placeholder = stringResource(R.string.explore_search_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Content
            when (val state = uiState) {
                is ExploreUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        HQCircularProgressIndicator(color = HQOrange, size = 36.dp, strokeWidth = 3.dp)
                    }
                }
                is ExploreUiState.Error -> {
                    HQEmptyState(
                        title = stringResource(R.string.error_boundary_title),
                        description = state.message,
                        actionLabel = stringResource(R.string.action_retry),
                        onActionClick = { viewModel.loadExplore() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is ExploreUiState.Success -> {
                    ExploreSuccessContent(
                        data = state.data,
                        selectedGenre = selectedGenre,
                        onGenreSelected = viewModel::onGenreSelected,
                        onComicClick = onComicClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ExploreSuccessContent(
    data: ExploreData,
    selectedGenre: String?,
    onGenreSelected: (String?) -> Unit,
    onComicClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Genre Chips
        if (data.genres.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    HQFilterChip(
                        label = stringResource(R.string.explore_all_genres),
                        selected = selectedGenre == null,
                        onClick = { onGenreSelected(null) }
                    )
                }
                items(data.genres) { genre ->
                    HQFilterChip(
                        label = genre.name,
                        selected = selectedGenre == genre.slug,
                        onClick = { onGenreSelected(genre.slug) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Results count
        BasicText(
            text = stringResource(R.string.explore_results_count, data.totalCount),
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 12.sp,
                color = HQTheme.colors.secondaryText
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Comics Grid
        if (data.comics.isEmpty()) {
            HQEmptyState(
                title = stringResource(R.string.explore_empty_title),
                description = stringResource(R.string.explore_empty_subtitle),
                icon = HQIcons.Search,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data.comics, key = { it.id }) { comic ->
                    ComicCard(
                        title = comic.title,
                        coverUrl = comic.coverUrl ?: "",
                        status = comic.status,
                        onClick = { onComicClick(comic.slug) }
                    )
                }
            }
        }
    }
}


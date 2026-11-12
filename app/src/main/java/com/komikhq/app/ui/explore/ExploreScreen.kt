package com.komikhq.app.ui.explore

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.komikhq.app.R
import com.komikhq.app.ui.components.comic.ComicCard
import com.komikhq.app.ui.components.core.HQFilterChip
import com.komikhq.app.ui.components.core.HQSearchField
import com.komikhq.app.ui.theme.HQTheme
import com.komikhq.app.ui.theme.InstrumentSansFamily
import com.komikhq.app.ui.theme.InterFamily

@Composable
fun ExploreScreen(
    onComicClick: (slug: String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // App Bar (64 dp) - Figma node 2:30
        ExploreTopAppBar(onFilterClick = onFilterClick)

        // Search Field - Figma node 2:417
        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)) {
            HQSearchField(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                placeholder = stringResource(R.string.explore_search_placeholder)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dynamic Genre Filter Chips (Figma node 2:422)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                HQFilterChip(
                    text = stringResource(R.string.explore_all_genres),
                    selected = uiState.selectedGenreSlug == null,
                    onClick = { viewModel.onGenreSelected(null) }
                )
            }
            items(uiState.genres, key = { it.id }) { genre ->
                HQFilterChip(
                    text = genre.name,
                    selected = uiState.selectedGenreSlug == genre.slug,
                    onClick = { viewModel.onGenreSelected(genre.slug) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subtitle (Figma node 2:428)
        Text(
            text = stringResource(R.string.explore_discover_subtitle),
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = HQTheme.colors.text,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Catalog Grid (2 columns) - Figma node 2:430
        if (uiState.isLoading && uiState.comics.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.comics.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_empty_search),
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    color = HQTheme.colors.secondaryText
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.comics, key = { it.id }) { comic ->
                    ComicCard(
                        comic = comic,
                        onClick = { onComicClick(comic.slug) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExploreTopAppBar(
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CompassCalibration,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.explore_title),
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = HQTheme.colors.text
            )
        }

        IconButton(onClick = onFilterClick) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(R.string.explore_filter_title),
                tint = HQTheme.colors.text,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


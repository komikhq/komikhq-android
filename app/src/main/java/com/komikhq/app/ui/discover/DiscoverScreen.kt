package com.komikhq.app.ui.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.komikhq.app.ui.components.comic.ComicCard
import com.komikhq.app.ui.components.comic.ComicCardVariant
import com.komikhq.app.ui.components.feedback.ErrorRetryCard
import com.komikhq.app.ui.components.feedback.LoadingIndicator

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    onComicClick: (comicId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DiscoverUiState.Loading -> {
                LoadingIndicator()
            }
            is DiscoverUiState.Error -> {
                ErrorRetryCard(
                    message = state.message,
                    onRetry = { viewModel.loadFeed() }
                )
            }
            is DiscoverUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Hero Trending Section
                    if (state.trending.isNotEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            Column {
                                Text(
                                    text = "Trending Now",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(state.trending) { comic ->
                                        ComicCard(
                                            comic = comic,
                                            onClick = { onComicClick(comic.id) },
                                            variant = ComicCardVariant.HORIZONTAL_HERO
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Latest Releases",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    // Latest Comics Grid
                    items(state.latest) { comic ->
                        ComicCard(
                            comic = comic,
                            onClick = { onComicClick(comic.id) },
                            variant = ComicCardVariant.PORTRAIT_GRID
                        )
                    }
                }
            }
        }
    }
}

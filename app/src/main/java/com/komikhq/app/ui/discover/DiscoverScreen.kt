package com.komikhq.app.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.komikhq.app.R
import com.komikhq.app.data.model.ComicItem
import com.komikhq.app.ui.common.KomikHQLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    onComicClick: (comicId: String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        KomikHQLogo(size = 32.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "KomikHQ",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Search will be implemented in discovery */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is DiscoverUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is DiscoverUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadFeed() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry")
                        }
                    }
                }
                is DiscoverUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
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
                                    Spacer(modifier = Modifier.height(12.dp))
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        items(state.trending) { comic ->
                                            TrendingCard(comic = comic, onClick = { onComicClick(comic.id) })
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
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
                            ComicGridCard(comic = comic, onClick = { onComicClick(comic.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingCard(
    comic: ComicItem,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = comic.coverUrl,
                placeholder = painterResource(id = R.drawable.ic_cover_placeholder),
                error = painterResource(id = R.drawable.ic_cover_placeholder),
                contentDescription = comic.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(95.dp)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    if (comic.type != null) {
                        Text(
                            text = comic.type,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                    Text(
                        text = comic.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "${comic.totalChapters} Ch.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ComicGridCard(
    comic: ComicItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        // Poster with badges
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = comic.coverUrl,
                placeholder = painterResource(id = R.drawable.ic_cover_placeholder),
                error = painterResource(id = R.drawable.ic_cover_placeholder),
                contentDescription = comic.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Type Badge Top-Left
            if (!comic.type.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = comic.type,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 9.sp
                    )
                }
            }

            // Chapter Count Badge Bottom-Right
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "${comic.totalChapters} Ch.",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(
            text = comic.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


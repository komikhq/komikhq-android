package com.komikhq.app.feature.library

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.komikhq.app.data.model.ComicItem

/**
 * KHQ 54 · Library Screen (Figma 2:38)
 * Tabs: Penanda / Riwayat / Unduhan
 * KHQ 48 · Library Empty States
 */
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel,
    onComicClick: (String) -> Unit,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                HQIcon(
                    imageVector = HQIcons.Library,
                    contentDescription = null,
                    tint = HQOrange,
                    modifier = Modifier.height(24.dp).width(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicText(
                    text = stringResource(R.string.library_title),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = HQTheme.colors.text
                    )
                )
            }

            // Tab Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LibraryTabChip(
                    label = stringResource(R.string.library_tab_bookmarks),
                    selected = selectedTab == LibraryTab.BOOKMARKS,
                    onClick = { viewModel.onTabSelected(LibraryTab.BOOKMARKS) }
                )
                LibraryTabChip(
                    label = stringResource(R.string.library_tab_history),
                    selected = selectedTab == LibraryTab.HISTORY,
                    onClick = { viewModel.onTabSelected(LibraryTab.HISTORY) }
                )
                LibraryTabChip(
                    label = stringResource(R.string.library_tab_downloads),
                    selected = selectedTab == LibraryTab.DOWNLOADS,
                    onClick = { viewModel.onTabSelected(LibraryTab.DOWNLOADS) }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Content
            when (val state = uiState) {
                is LibraryUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HQCircularProgressIndicator(color = HQOrange, size = 36.dp, strokeWidth = 3.dp)
                    }
                }
                is LibraryUiState.Error -> {
                    HQEmptyState(
                        title = stringResource(R.string.error_boundary_title),
                        description = state.message,
                        actionLabel = stringResource(R.string.action_retry),
                        onActionClick = { viewModel.loadLibrary() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is LibraryUiState.Success -> {
                    val currentItems = when (selectedTab) {
                        LibraryTab.BOOKMARKS -> state.data.bookmarks
                        LibraryTab.HISTORY -> state.data.history
                        LibraryTab.DOWNLOADS -> state.data.downloads
                    }
                    if (currentItems.isEmpty()) {
                        LibraryEmptyContent(
                            tab = selectedTab,
                            onExploreClick = onExploreClick
                        )
                    } else {
                        LibraryGrid(
                            comics = currentItems,
                            onComicClick = onComicClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LibraryTabChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) HQOrange else HQTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp,
                color = if (selected) androidx.compose.ui.graphics.Color.White else HQTheme.colors.secondaryText
            )
        )
    }
}

@Composable
private fun LibraryGrid(
    comics: List<ComicItem>,
    onComicClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(comics, key = { it.id }) { comic ->
            ComicCard(
                title = comic.title,
                coverUrl = comic.coverUrl ?: "",
                status = comic.status,
                onClick = { onComicClick(comic.slug) }
            )
        }
    }
}

@Composable
private fun LibraryEmptyContent(
    tab: LibraryTab,
    onExploreClick: () -> Unit
) {
    val (titleRes, descRes, icon) = when (tab) {
        LibraryTab.BOOKMARKS -> Triple(
            R.string.library_empty_bookmarks_title,
            R.string.library_empty_bookmarks_desc,
            HQIcons.Bookmark
        )
        LibraryTab.HISTORY -> Triple(
            R.string.library_empty_history_title,
            R.string.library_empty_history_desc,
            HQIcons.History
        )
        LibraryTab.DOWNLOADS -> Triple(
            R.string.library_empty_downloads_title,
            R.string.library_empty_downloads_desc,
            HQIcons.Download
        )
    }

    HQEmptyState(
        title = stringResource(titleRes),
        description = stringResource(descRes),
        icon = icon,
        actionLabel = stringResource(R.string.library_empty_explore_cta),
        onActionClick = onExploreClick,
        modifier = Modifier.fillMaxSize()
    )
}


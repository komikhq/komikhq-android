package com.komikhq.app.ui.library

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.komikhq.app.R
import com.komikhq.app.ui.components.comic.ComicCard
import com.komikhq.app.ui.theme.HQTheme
import com.komikhq.app.ui.theme.InstrumentSansFamily
import com.komikhq.app.ui.theme.InterFamily

@Composable
fun LibraryScreen(
    onComicClick: (slug: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // App Bar (64 dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.library_title),
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = HQTheme.colors.text
            )
        }

        // Segmented Tabs Control (Figma node 2:160)
        LibrarySegmentedTabs(
            selectedTab = uiState.selectedTab,
            onTabSelected = viewModel::onTabSelected,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        val currentList = when (uiState.selectedTab) {
            LibraryTab.BOOKMARKS -> uiState.bookmarks
            LibraryTab.HISTORY -> uiState.history
            LibraryTab.DOWNLOADS -> uiState.downloads
        }

        val emptyMessageRes = when (uiState.selectedTab) {
            LibraryTab.BOOKMARKS -> R.string.library_empty_bookmarks
            LibraryTab.HISTORY -> R.string.library_empty_history
            LibraryTab.DOWNLOADS -> R.string.library_empty_downloads
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (currentList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(emptyMessageRes),
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
                items(currentList, key = { it.id }) { comic ->
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
private fun LibrarySegmentedTabs(
    selectedTab: LibraryTab,
    onTabSelected: (LibraryTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)

    Surface(
        shape = shape,
        color = HQTheme.colors.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, HQTheme.colors.border),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            LibraryTabItem(
                title = stringResource(R.string.library_tab_bookmarks),
                isSelected = selectedTab == LibraryTab.BOOKMARKS,
                onClick = { onTabSelected(LibraryTab.BOOKMARKS) },
                modifier = Modifier.weight(1f)
            )
            LibraryTabItem(
                title = stringResource(R.string.library_tab_history),
                isSelected = selectedTab == LibraryTab.HISTORY,
                onClick = { onTabSelected(LibraryTab.HISTORY) },
                modifier = Modifier.weight(1f)
            )
            LibraryTabItem(
                title = stringResource(R.string.library_tab_downloads),
                isSelected = selectedTab == LibraryTab.DOWNLOADS,
                onClick = { onTabSelected(LibraryTab.DOWNLOADS) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LibraryTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(3.dp)
            .clip(shape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontFamily = InterFamily,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            fontSize = 13.sp,
            color = if (isSelected) Color.White else HQTheme.colors.secondaryText
        )
    }
}


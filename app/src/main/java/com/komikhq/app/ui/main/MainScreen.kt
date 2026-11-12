package com.komikhq.app.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.komikhq.app.ui.components.core.HQBottomNavigation
import com.komikhq.app.ui.components.core.HQNavTab
import com.komikhq.app.ui.explore.ExploreScreen
import com.komikhq.app.ui.home.HomeScreen
import com.komikhq.app.ui.library.LibraryScreen
import com.komikhq.app.ui.theme.HQTheme

@Composable
fun MainScreen(
    onComicClick: (slug: String) -> Unit,
    onChapterClick: (comicSlug: String, chapterSlug: String) -> Unit,
    onNotificationsClick: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(HQNavTab.HOME) }

    Scaffold(
        bottomBar = {
            HQBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        containerColor = HQTheme.colors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(HQTheme.colors.background)
        ) {
            when (selectedTab) {
                HQNavTab.HOME -> {
                    HomeScreen(
                        onComicClick = onComicClick,
                        onContinueReadingClick = onChapterClick,
                        onHistoryClick = { selectedTab = HQNavTab.LIBRARY },
                        onNotificationsClick = onNotificationsClick
                    )
                }
                HQNavTab.EXPLORE -> {
                    ExploreScreen(
                        onComicClick = onComicClick,
                        onFilterClick = { /* Filter drawer/sheet */ }
                    )
                }
                HQNavTab.LIBRARY -> {
                    LibraryScreen(
                        onComicClick = onComicClick
                    )
                }
            }
        }
    }
}

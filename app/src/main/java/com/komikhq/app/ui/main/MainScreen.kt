package com.komikhq.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.komikhq.app.data.local.TokenStorage
import com.komikhq.app.ui.common.KomikHQLogo
import com.komikhq.app.ui.common.LogoSize
import com.komikhq.app.ui.common.LogoVariant
import com.komikhq.app.ui.components.feedback.EmptyContentState
import com.komikhq.app.ui.discover.DiscoverScreen
import com.komikhq.app.ui.profile.ProfileScreen

enum class MainTab(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    DISCOVER("Discover", Icons.Filled.Explore, Icons.Outlined.Explore),
    BOOKMARKS("Bookmarks", Icons.Filled.Bookmark, Icons.Filled.BookmarkBorder),
    HISTORY("History", Icons.Filled.History, Icons.Outlined.History),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    tokenStorage: TokenStorage,
    onLogout: () -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentTab = MainTab.entries[selectedTabIndex]

    Scaffold(
        topBar = {
            when (currentTab) {
                MainTab.DISCOVER -> {
                    TopAppBar(
                        title = {
                            KomikHQLogo(
                                variant = LogoVariant.FULL,
                                size = LogoSize.HEADER
                            )
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
                }
                MainTab.BOOKMARKS -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Bookmarks",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                MainTab.HISTORY -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Reading History",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                MainTab.PROFILE -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Profile & Settings",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 1.dp
                )
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    MainTab.entries.forEachIndexed { index, tab ->
                        val isSelected = selectedTabIndex == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.label
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MainTab.DISCOVER -> DiscoverScreen()
                MainTab.BOOKMARKS -> EmptyContentState(
                    title = "Bookmarks",
                    message = "Your saved comics will appear here."
                )
                MainTab.HISTORY -> EmptyContentState(
                    title = "Reading History",
                    message = "Your recent chapters and pages will appear here."
                )
                MainTab.PROFILE -> ProfileScreen(
                    tokenStorage = tokenStorage,
                    onLogout = onLogout
                )
            }
        }
    }
}

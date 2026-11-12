package com.komikhq.app.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.ui.HQBottomNavigation
import com.komikhq.app.core.ui.HQNavTab
import com.komikhq.app.navigation.AppNavHost
import com.komikhq.app.navigation.Routes

/**
 * Root application scaffold wrapping AppNavHost with custom HQBottomNavigation.
 * This is the single top-level composable. The bottom nav is shown
 * only when the user is on Home, Explore, or Library.
 */
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.ONBOARDING_SPLASH,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine which tab is selected (null = hide bottom nav)
    val selectedTab = when (currentRoute) {
        Routes.HOME, Routes.HOME_EDITORIAL -> HQNavTab.HOME
        Routes.EXPLORE -> HQNavTab.EXPLORE
        Routes.LIBRARY -> HQNavTab.LIBRARY
        else -> null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
    ) {
        // Single NavHost for all routes
        AppNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize()
        )

        // Bottom Nav — only on main tab screens
        if (selectedTab != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            ) {
                HQBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        val route = when (tab) {
                            HQNavTab.HOME -> Routes.HOME
                            HQNavTab.EXPLORE -> Routes.EXPLORE
                            HQNavTab.LIBRARY -> Routes.LIBRARY
                        }
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(Routes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

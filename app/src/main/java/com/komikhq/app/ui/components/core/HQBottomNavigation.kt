package com.komikhq.app.ui.components.core

typealias HQNavTab = com.komikhq.app.core.ui.HQNavTab

@androidx.compose.runtime.Composable
fun HQBottomNavigation(
    selectedTab: com.komikhq.app.core.ui.HQNavTab,
    onTabSelected: (com.komikhq.app.core.ui.HQNavTab) -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    com.komikhq.app.core.ui.HQBottomNavigation(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        modifier = modifier
    )
}

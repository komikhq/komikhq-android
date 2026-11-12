package com.komikhq.app.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.outlined.CompassCalibration
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.ui.theme.HQOrange
import com.komikhq.app.ui.theme.HQTheme
import com.komikhq.app.ui.theme.InterFamily

enum class HQNavTab(
    val stringResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(R.string.nav_home, Icons.Filled.Home, Icons.Outlined.Home),
    EXPLORE(R.string.nav_explore, Icons.Outlined.CompassCalibration, Icons.Outlined.CompassCalibration),
    LIBRARY(R.string.nav_library, Icons.Filled.LocalLibrary, Icons.Outlined.LocalLibrary)
}

/**
 * 1:1 Pixel-exact implementation of Figma Node 2:164 (Active=Home) and 2:692.
 * Custom Compose layout without Material 3 NavigationBar to eliminate cropping and rigid insets.
 */
@Composable
fun HQBottomNavigation(
    selectedTab: HQNavTab,
    onTabSelected: (HQNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(HQTheme.colors.surface)
            .navigationBarsPadding()
    ) {
        // Top Divider 1 dp (Figma node 2:165)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(HQTheme.colors.border)
        )

        // Navigation Items Row (Height: 82 dp total including padding)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HQNavTab.entries.forEach { tab ->
                val isSelected = selectedTab == tab
                HQBottomNavItem(
                    label = stringResource(tab.stringResId),
                    icon = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                    isSelected = isSelected,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun HQBottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        // Active Indicator Pill (64 x 30 dp, radius 15 dp) - Figma node 2:166
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 30.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (isSelected) HQTheme.colors.background else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) HQOrange else HQTheme.colors.secondaryText,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Label 11 sp - Figma node 2:170
        Text(
            text = label,
            fontFamily = InterFamily,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 11.sp,
            color = if (isSelected) HQOrange else HQTheme.colors.secondaryText
        )
    }
}

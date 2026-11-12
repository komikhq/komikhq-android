package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

/**
 * Custom 36dp Filter Chip without Material 3 dependencies.
 * 1:1 match for Figma Explore & Filter Sheet specifications.
 */
@Composable
fun HQFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = modifier
            .height(36.dp)
            .clip(shape)
            .background(
                if (selected) HQOrange else HQTheme.colors.surface
            )
            .then(
                if (!selected) Modifier.border(1.dp, HQTheme.colors.border, shape)
                else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                fontSize = 13.sp,
                color = if (selected) Color.White else HQTheme.colors.secondaryText
            )
        )
    }
}

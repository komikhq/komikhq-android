package com.komikhq.app.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

enum class HQBadgeVariant {
    SOLID_PRIMARY,
    SOLID_ERROR,
    TRANSLUCENT,
    OUTLINE
}

@Composable
fun HQBadge(
    text: String,
    modifier: Modifier = Modifier,
    variant: HQBadgeVariant = HQBadgeVariant.SOLID_PRIMARY
) {
    val shape = RoundedCornerShape(6.dp)

    val backgroundColor: Color
    val textColor: Color
    val borderColor: Color?

    when (variant) {
        HQBadgeVariant.SOLID_PRIMARY -> {
            backgroundColor = HQOrange
            textColor = Color.White
            borderColor = null
        }
        HQBadgeVariant.SOLID_ERROR -> {
            backgroundColor = ErrorRed
            textColor = Color.White
            borderColor = null
        }
        HQBadgeVariant.TRANSLUCENT -> {
            backgroundColor = HQTheme.colors.surface.copy(alpha = 0.85f)
            textColor = HQTheme.colors.text
            borderColor = null
        }
        HQBadgeVariant.OUTLINE -> {
            backgroundColor = Color.Transparent
            textColor = HQTheme.colors.secondaryText
            borderColor = HQTheme.colors.border
        }
    }

    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (borderColor != null) Modifier.border(1.dp, borderColor, shape) else Modifier
            )
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 10.sp
            )
        )
    }
}

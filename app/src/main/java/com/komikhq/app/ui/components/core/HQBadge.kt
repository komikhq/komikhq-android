package com.komikhq.app.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// HQBadge — KomikHQ Design System Badge Primitive
//
// Usage:
//   HQBadge("VERIFIED")
//   HQBadge("MANGA", variant = HQBadgeVariant.SOLID_PRIMARY)
//   HQBadge("Error", variant = HQBadgeVariant.SOLID_ERROR)
//   HQBadge("12 Ch.", variant = HQBadgeVariant.TRANSLUCENT)
//   HQBadge("Action", variant = HQBadgeVariant.OUTLINE)
// ============================================================

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
            backgroundColor = MaterialTheme.colorScheme.primary
            textColor = MaterialTheme.colorScheme.onPrimary
            borderColor = null
        }
        HQBadgeVariant.SOLID_ERROR -> {
            backgroundColor = MaterialTheme.colorScheme.error
            textColor = MaterialTheme.colorScheme.onError
            borderColor = null
        }
        HQBadgeVariant.TRANSLUCENT -> {
            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
            textColor = MaterialTheme.colorScheme.onSurface
            borderColor = null
        }
        HQBadgeVariant.OUTLINE -> {
            backgroundColor = Color.Transparent
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
            borderColor = MaterialTheme.colorScheme.outline
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
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontSize = 9.sp
        )
    }
}

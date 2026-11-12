package com.komikhq.app.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme

// ============================================================
// HQSkeleton — KomikHQ Design System Shimmer Loading Placeholder
//
// Usage:
//   HQSkeleton(modifier = Modifier.fillMaxWidth().height(200.dp))  // block
//   HQSkeletonPoster()                                              // comic poster placeholder
//   HQSkeletonText()                                                // single line text placeholder
//   HQSkeletonCard()                                                // full comic card placeholder
// ============================================================

@Composable
fun HQSkeleton(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAlpha"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = alpha))
    )
}

@Composable
fun HQSkeletonPoster(
    modifier: Modifier = Modifier
) {
    HQSkeleton(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.72f),
        cornerRadius = 12.dp
    )
}

@Composable
fun HQSkeletonText(
    modifier: Modifier = Modifier,
    widthFraction: Float = 0.7f,
    height: Dp = 14.dp
) {
    HQSkeleton(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .height(height),
        cornerRadius = 4.dp
    )
}

@Composable
fun HQSkeletonCard(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HQSkeletonPoster()
        Spacer(modifier = Modifier.height(8.dp))
        HQSkeletonText(widthFraction = 0.85f)
        Spacer(modifier = Modifier.height(4.dp))
        HQSkeletonText(widthFraction = 0.55f)
    }
}


package com.komikhq.app.core.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.komikhq.app.core.designsystem.HQTheme

/**
 * Shimmer modifier applying animated gradient across placeholder bounds.
 */
@Composable
fun Modifier.hqShimmer(): Modifier {
    val transition = rememberInfiniteTransition(label = "hqShimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hqShimmerTranslate"
    )

    val baseColor = HQTheme.colors.surface
    val highlightColor = HQTheme.colors.border

    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(translateAnim.value - 200f, translateAnim.value - 200f),
        end = Offset(translateAnim.value + 200f, translateAnim.value + 200f)
    )

    return this.background(brush)
}

@Composable
fun HQSkeletonBox(
    modifier: Modifier = Modifier,
    shapeRadius: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(shapeRadius))
            .hqShimmer()
    )
}

/**
 * Shimmer bone for Hero Carousel item (364 x 248 dp).
 */
@Composable
fun HQHeroSkeleton(modifier: Modifier = Modifier) {
    HQSkeletonBox(
        modifier = modifier
            .fillMaxWidth()
            .height(248.dp),
        shapeRadius = 16.dp
    )
}

/**
 * Shimmer bone for Comic Card item.
 */
@Composable
fun HQComicCardSkeleton(
    modifier: Modifier = Modifier,
    cardWidth: Dp = 152.dp
) {
    Column(modifier = modifier.width(cardWidth)) {
        HQSkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f),
            shapeRadius = 12.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        HQSkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp),
            shapeRadius = 4.dp
        )
        Spacer(modifier = Modifier.height(4.dp))
        HQSkeletonBox(
            modifier = Modifier
                .width(cardWidth * 0.6f)
                .height(12.dp),
            shapeRadius = 4.dp
        )
    }
}

/**
 * Shimmer bone for Chapter Row item (68 dp).
 */
@Composable
fun HQChapterRowSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            HQSkeletonBox(modifier = Modifier.size(8.dp), shapeRadius = 4.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                HQSkeletonBox(modifier = Modifier.width(160.dp).height(14.dp), shapeRadius = 4.dp)
                Spacer(modifier = Modifier.height(6.dp))
                HQSkeletonBox(modifier = Modifier.width(80.dp).height(10.dp), shapeRadius = 4.dp)
            }
        }
        HQSkeletonBox(modifier = Modifier.size(24.dp), shapeRadius = 12.dp)
    }
}


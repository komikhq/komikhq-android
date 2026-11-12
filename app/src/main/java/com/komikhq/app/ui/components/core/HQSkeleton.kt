package com.komikhq.app.ui.components.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.komikhq.app.core.ui.hqShimmer as coreHqShimmer

@Composable
fun Modifier.hqShimmer(): Modifier = this.coreHqShimmer()

@Composable
fun HQSkeletonBox(
    modifier: Modifier = Modifier,
    shapeRadius: Dp = 8.dp
) {
    com.komikhq.app.core.ui.HQSkeletonBox(modifier = modifier, shapeRadius = shapeRadius)
}

@Composable
fun HQHeroSkeleton(modifier: Modifier = Modifier) {
    com.komikhq.app.core.ui.HQHeroSkeleton(modifier = modifier)
}

@Composable
fun HQComicCardSkeleton(
    modifier: Modifier = Modifier,
    cardWidth: Dp = 152.dp
) {
    com.komikhq.app.core.ui.HQComicCardSkeleton(modifier = modifier, cardWidth = cardWidth)
}

@Composable
fun HQChapterRowSkeleton(modifier: Modifier = Modifier) {
    com.komikhq.app.core.ui.HQChapterRowSkeleton(modifier = modifier)
}

package com.komikhq.app.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.komikhq.app.core.designsystem.HQOrange

/**
 * 100% Custom Circular Progress Indicator without Material 3 dependency.
 * Renders smooth spinning arc on Canvas.
 */
@Composable
fun HQCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = HQOrange,
    strokeWidth: Dp = 2.5.dp,
    size: Dp = 24.dp
) {
    val transition = rememberInfiniteTransition(label = "HQCircularProgress")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "HQCircularProgressRotation"
    )

    Canvas(modifier = modifier.size(size)) {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )

        // Track background
        drawCircle(
            color = color.copy(alpha = 0.2f),
            style = stroke
        )

        // Spinning indicator
        drawArc(
            color = color,
            startAngle = rotation,
            sweepAngle = 270f,
            useCenter = false,
            style = stroke
        )
    }
}


package com.komikhq.app.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Custom HQIcon component that renders pure Compose ImageVector without any Material UI dependency.
 */
@Composable
fun HQIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = HQTheme.colors.text
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(tint)
    )
}

/**
 * 100% Custom Vector Icons matching Figma specifications.
 * Zero dependency on Material Icon libraries.
 */
object HQIcons {

    val Home: ImageVector by lazy {
        ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.White),
            stroke = null
        ) {
            moveTo(10f, 20f)
            verticalLineToRelative(-6f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(5f)
            verticalLineToRelative(-8f)
            horizontalLineToRelative(3f)
            lineTo(12f, 3f)
            lineTo(2f, 12f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(8f)
            close()
        }.build()
    }

    val HomeOutlined: ImageVector by lazy {
        ImageVector.Builder(
            name = "HomeOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(3f, 9.5f)
            lineTo(12f, 3f)
            lineTo(21f, 9.5f)
            verticalLineTo(20f)
            curveTo(21f, 20.55f, 20.55f, 21f, 20f, 21f)
            horizontalLineTo(4f)
            curveTo(3.45f, 21f, 3f, 20.55f, 3f, 20f)
            close()
            moveTo(9f, 21f)
            verticalLineTo(12f)
            horizontalLineTo(15f)
            verticalLineTo(21f)
        }.build()
    }

    val Compass: ImageVector by lazy {
        ImageVector.Builder(
            name = "Compass",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Circle
            moveTo(12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            close()
            // Needle
            moveTo(16.24f, 7.76f)
            lineTo(14.12f, 14.12f)
            lineTo(7.76f, 16.24f)
            lineTo(9.88f, 9.88f)
            close()
        }.build()
    }

    val Library: ImageVector by lazy {
        ImageVector.Builder(
            name = "Library",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4f, 19.5f)
            curveTo(4f, 18.12f, 5.12f, 17f, 6.5f, 17f)
            horizontalLineTo(20f)
            moveTo(6.5f, 2f)
            horizontalLineTo(20f)
            verticalLineTo(22f)
            horizontalLineTo(6.5f)
            curveTo(5.12f, 22f, 4f, 20.88f, 4f, 19.5f)
            verticalLineTo(4.5f)
            curveTo(4f, 3.12f, 5.12f, 2f, 6.5f, 2f)
            close()
        }.build()
    }

    val Search: ImageVector by lazy {
        ImageVector.Builder(
            name = "Search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(11f, 19f)
            curveTo(15.42f, 19f, 19f, 15.42f, 19f, 11f)
            curveTo(19f, 6.58f, 15.42f, 3f, 11f, 3f)
            curveTo(6.58f, 3f, 3f, 6.58f, 3f, 11f)
            curveTo(3f, 15.42f, 6.58f, 19f, 11f, 19f)
            close()
            moveTo(21f, 21f)
            lineTo(16.65f, 16.65f)
        }.build()
    }

    val Close: ImageVector by lazy {
        ImageVector.Builder(
            name = "Close",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 6f)
            lineTo(6f, 18f)
            moveTo(6f, 6f)
            lineTo(18f, 18f)
        }.build()
    }

    val ArrowBack: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19f, 12f)
            horizontalLineTo(5f)
            moveTo(12f, 19f)
            lineTo(5f, 12f)
            lineTo(12f, 5f)
        }.build()
    }

    val ChevronRight: ImageVector by lazy {
        ImageVector.Builder(
            name = "ChevronRight",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9f, 18f)
            lineTo(15f, 12f)
            lineTo(9f, 6f)
        }.build()
    }

    val MoreVertical: ImageVector by lazy {
        ImageVector.Builder(
            name = "MoreVertical",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.White),
            stroke = null
        ) {
            moveTo(12f, 8f)
            curveTo(13.1f, 8f, 14f, 7.1f, 14f, 6f)
            curveTo(14f, 4.9f, 13.1f, 4f, 12f, 4f)
            curveTo(10.9f, 4f, 10f, 4.9f, 10f, 6f)
            curveTo(10f, 7.1f, 10.9f, 8f, 12f, 8f)
            close()
            moveTo(12f, 14f)
            curveTo(13.1f, 14f, 14f, 13.1f, 14f, 12f)
            curveTo(14f, 10.9f, 13.1f, 10f, 12f, 10f)
            curveTo(10.9f, 10f, 10f, 10.9f, 10f, 12f)
            curveTo(10f, 13.1f, 10.9f, 14f, 12f, 14f)
            close()
            moveTo(12f, 20f)
            curveTo(13.1f, 20f, 14f, 19.1f, 14f, 18f)
            curveTo(14f, 16.9f, 13.1f, 16f, 12f, 16f)
            curveTo(10.9f, 16f, 10f, 16.9f, 10f, 18f)
            curveTo(10f, 19.1f, 10.9f, 20f, 12f, 20f)
            close()
        }.build()
    }

    val Filter: ImageVector by lazy {
        ImageVector.Builder(
            name = "Filter",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4f, 21f)
            verticalLineTo(14f)
            moveTo(4f, 10f)
            verticalLineTo(3f)
            moveTo(12f, 21f)
            verticalLineTo(12f)
            moveTo(12f, 8f)
            verticalLineTo(3f)
            moveTo(20f, 21f)
            verticalLineTo(16f)
            moveTo(20f, 12f)
            verticalLineTo(3f)
            moveTo(1f, 14f)
            horizontalLineTo(7f)
            moveTo(9f, 8f)
            horizontalLineTo(15f)
            moveTo(17f, 16f)
            horizontalLineTo(23f)
        }.build()
    }

    val Star: ImageVector by lazy {
        ImageVector.Builder(
            name = "Star",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.White),
            stroke = null
        ) {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineTo(16.54f, 13.97f)
            lineTo(22f, 9.24f)
            lineTo(14.81f, 8.63f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineTo(7.46f, 13.97f)
            lineTo(5.82f, 21f)
            close()
        }.build()
    }

    val Download: ImageVector by lazy {
        ImageVector.Builder(
            name = "Download",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(21f, 15f)
            verticalLineTo(19f)
            curveTo(21f, 20.1f, 20.1f, 21f, 19f, 21f)
            horizontalLineTo(5f)
            curveTo(3.9f, 21f, 3f, 20.1f, 3f, 19f)
            verticalLineTo(15f)
            moveTo(7f, 10f)
            lineTo(12f, 15f)
            lineTo(17f, 10f)
            moveTo(12f, 15f)
            verticalLineTo(3f)
        }.build()
    }

    val DownloadDone: ImageVector by lazy {
        ImageVector.Builder(
            name = "DownloadDone",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(20f, 6f)
            lineTo(9f, 17f)
            lineTo(4f, 12f)
        }.build()
    }

    val Eye: ImageVector by lazy {
        ImageVector.Builder(
            name = "Eye",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(1f, 12f)
            curveTo(3.5f, 6.5f, 7.5f, 3f, 12f, 3f)
            curveTo(16.5f, 3f, 20.5f, 6.5f, 23f, 12f)
            curveTo(20.5f, 17.5f, 16.5f, 21f, 12f, 21f)
            curveTo(7.5f, 21f, 3.5f, 17.5f, 1f, 12f)
            close()
            moveTo(12f, 15f)
            curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
            curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
            curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
            curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
            close()
        }.build()
    }

    val EyeOff: ImageVector by lazy {
        ImageVector.Builder(
            name = "EyeOff",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(17.94f, 17.94f)
            curveTo(16.23f, 19.24f, 14.18f, 20f, 12f, 20f)
            curveTo(7.5f, 20f, 3.5f, 16.5f, 1f, 11f)
            curveTo(2.16f, 8.44f, 4.02f, 6.34f, 6.36f, 4.98f)
            moveTo(9.9f, 4.24f)
            curveTo(10.58f, 4.08f, 11.28f, 4f, 12f, 4f)
            curveTo(16.5f, 4f, 20.5f, 7.5f, 23f, 13f)
            curveTo(22.18f, 14.8f, 20.94f, 16.36f, 19.42f, 17.55f)
            moveTo(1f, 1f)
            lineTo(23f, 23f)
        }.build()
    }

    val Bell: ImageVector by lazy {
        ImageVector.Builder(
            name = "Bell",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 8f)
            curveTo(18f, 4.69f, 15.31f, 2f, 12f, 2f)
            curveTo(8.69f, 2f, 6f, 4.69f, 6f, 8f)
            curveTo(6f, 15f, 3f, 17f, 3f, 17f)
            horizontalLineTo(21f)
            curveTo(21f, 17f, 18f, 15f, 18f, 8f)
            close()
            moveTo(13.73f, 21f)
            curveTo(13.38f, 21.61f, 12.74f, 22f, 12f, 22f)
            curveTo(11.26f, 22f, 10.62f, 21.61f, 10.27f, 21f)
        }.build()
    }

    val Bookmark: ImageVector by lazy {
        ImageVector.Builder(
            name = "Bookmark",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19f, 21f)
            lineTo(12f, 16f)
            lineTo(5f, 21f)
            verticalLineTo(5f)
            curveTo(5f, 3.9f, 5.9f, 3f, 7f, 3f)
            horizontalLineTo(17f)
            curveTo(18.1f, 3f, 19f, 3.9f, 19f, 5f)
            verticalLineTo(21f)
            close()
        }.build()
    }

    val BookmarkFilled: ImageVector by lazy {
        ImageVector.Builder(
            name = "BookmarkFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.White),
            stroke = null
        ) {
            moveTo(17f, 3f)
            horizontalLineTo(7f)
            curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
            verticalLineTo(21f)
            lineTo(12f, 18f)
            lineTo(19f, 21f)
            verticalLineTo(5f)
            curveTo(19f, 3.9f, 18.1f, 3f, 17f, 3f)
            close()
        }.build()
    }

    val User: ImageVector by lazy {
        ImageVector.Builder(
            name = "User",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(20f, 21f)
            verticalLineTo(19f)
            curveTo(20f, 16.79f, 17.31f, 15f, 14f, 15f)
            horizontalLineTo(10f)
            curveTo(6.69f, 15f, 4f, 16.79f, 4f, 19f)
            verticalLineTo(21f)
            moveTo(12f, 11f)
            curveTo(14.21f, 11f, 16f, 9.21f, 16f, 7f)
            curveTo(16f, 4.79f, 14.21f, 3f, 12f, 3f)
            curveTo(9.79f, 3f, 8f, 4.79f, 8f, 7f)
            curveTo(8f, 9.21f, 9.79f, 11f, 12f, 11f)
            close()
        }.build()
    }

    val Settings: ImageVector by lazy {
        ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 15f)
            curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
            curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
            curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
            curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
            close()
            moveTo(19.4f, 15f)
            lineTo(20.9f, 16.2f)
            lineTo(19.3f, 19f)
            lineTo(17.4f, 18.2f)
            lineTo(15.9f, 19.8f)
            lineTo(15.9f, 22f)
            lineTo(12.7f, 22f)
            lineTo(12.7f, 19.8f)
            lineTo(11.2f, 18.2f)
            lineTo(9.3f, 19f)
            lineTo(7.7f, 16.2f)
            lineTo(9.2f, 15f)
            lineTo(8.8f, 13.1f)
            lineTo(6.6f, 13.1f)
            lineTo(6.6f, 10.9f)
            lineTo(8.8f, 10.9f)
            lineTo(9.2f, 9f)
            lineTo(7.7f, 7.8f)
            lineTo(9.3f, 5f)
            lineTo(11.2f, 5.8f)
            lineTo(12.7f, 4.2f)
            lineTo(12.7f, 2f)
            lineTo(15.9f, 2f)
            lineTo(15.9f, 4.2f)
            lineTo(17.4f, 5.8f)
            lineTo(19.3f, 5f)
            lineTo(20.9f, 7.8f)
            lineTo(19.4f, 9f)
            lineTo(19.8f, 10.9f)
            lineTo(22f, 10.9f)
            lineTo(22f, 13.1f)
            lineTo(19.8f, 13.1f)
            close()
        }.build()
    }

    val History: ImageVector by lazy {
        ImageVector.Builder(
            name = "History",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 8f)
            verticalLineTo(12f)
            lineTo(15f, 15f)
            moveTo(3.05f, 11f)
            curveTo(3.56f, 6.49f, 7.37f, 3f, 12f, 3f)
            curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
            curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
            curveTo(8.07f, 21f, 4.75f, 18.48f, 3.49f, 14.95f)
            moveTo(3f, 5f)
            verticalLineTo(11f)
            horizontalLineTo(9f)
        }.build()
    }

    val Trash: ImageVector by lazy {
        ImageVector.Builder(
            name = "Trash",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(3f, 6f)
            horizontalLineTo(21f)
            moveTo(19f, 6f)
            verticalLineTo(20f)
            curveTo(19f, 21.1f, 18.1f, 22f, 17f, 22f)
            horizontalLineTo(7f)
            curveTo(5.9f, 22f, 5f, 21.1f, 5f, 20f)
            verticalLineTo(6f)
            moveTo(8f, 6f)
            verticalLineTo(4f)
            curveTo(8f, 2.9f, 8.9f, 2f, 10f, 2f)
            horizontalLineTo(14f)
            curveTo(15.1f, 2f, 16f, 2.9f, 16f, 4f)
            verticalLineTo(6f)
        }.build()
    }

    val LogOut: ImageVector by lazy {
        ImageVector.Builder(
            name = "LogOut",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9f, 21f)
            horizontalLineTo(5f)
            curveTo(3.9f, 21f, 3f, 20.1f, 3f, 19f)
            verticalLineTo(5f)
            curveTo(3f, 3.9f, 3.9f, 3f, 5f, 3f)
            horizontalLineTo(9f)
            moveTo(16f, 17f)
            lineTo(21f, 12f)
            lineTo(16f, 7f)
            moveTo(21f, 12f)
            horizontalLineTo(9f)
        }.build()
    }

    val CheckCircle: ImageVector by lazy {
        ImageVector.Builder(
            name = "CheckCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(22f, 11.08f)
            verticalLineTo(12f)
            curveTo(22f, 17.52f, 17.52f, 22f, 12f, 22f)
            curveTo(6.48f, 22f, 2f, 17.52f, 2f, 12f)
            curveTo(2f, 6.48f, 6.48f, 2f, 12f, 2f)
            curveTo(15.06f, 2f, 17.82f, 3.42f, 19.64f, 5.64f)
            moveTo(22f, 4f)
            lineTo(12f, 14.01f)
            lineTo(9f, 11.01f)
        }.build()
    }

    val Shield: ImageVector by lazy {
        ImageVector.Builder(
            name = "Shield",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 22f)
            curveTo(12f, 22f, 20f, 18f, 20f, 12f)
            verticalLineTo(5f)
            lineTo(12f, 2f)
            lineTo(4f, 5f)
            verticalLineTo(12f)
            curveTo(4f, 18f, 12f, 22f, 12f, 22f)
            close()
        }.build()
    }

    val Brightness: ImageVector by lazy {
        ImageVector.Builder(
            name = "Brightness",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = null,
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 17f)
            curveTo(14.76f, 17f, 17f, 14.76f, 17f, 12f)
            curveTo(17f, 9.24f, 14.76f, 7f, 12f, 7f)
            curveTo(9.24f, 7f, 7f, 9.24f, 7f, 12f)
            curveTo(7f, 14.76f, 9.24f, 17f, 12f, 17f)
            close()
            moveTo(12f, 1f)
            verticalLineTo(3f)
            moveTo(12f, 21f)
            verticalLineTo(23f)
            moveTo(4.22f, 4.22f)
            lineTo(5.64f, 5.64f)
            moveTo(18.36f, 18.36f)
            lineTo(19.78f, 19.78f)
            moveTo(1f, 12f)
            horizontalLineTo(3f)
            moveTo(21f, 12f)
            horizontalLineTo(23f)
            moveTo(4.22f, 19.78f)
            lineTo(5.64f, 18.36f)
            moveTo(18.36f, 5.64f)
            lineTo(19.78f, 4.22f)
        }.build()
    }
}


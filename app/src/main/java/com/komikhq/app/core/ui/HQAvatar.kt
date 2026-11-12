package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

/**
 * Custom Avatar component with fallback initials or user icon. Zero Material 3 dependency.
 */
@Composable
fun HQAvatar(
    avatarUrl: String? = null,
    displayName: String? = null,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    onClick: (() -> Unit)? = null,
    imageUrl: String? = null,
    name: String? = null
) {
    val finalUrl = avatarUrl ?: imageUrl
    val finalName = displayName ?: name

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(HQTheme.colors.surface)
            .border(1.5.dp, HQTheme.colors.border, CircleShape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (!finalUrl.isNullOrBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(finalUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = finalName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else if (!finalName.isNullOrBlank()) {
            val initial = finalName.trim().take(1).uppercase()
            BasicText(
                text = initial,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.4f).sp,
                    color = HQOrange
                )
            )
        } else {
            HQIcon(
                imageVector = HQIcons.User,
                contentDescription = null,
                tint = HQTheme.colors.secondaryText,
                modifier = Modifier.size(size * 0.55f)
            )
        }
    }
}


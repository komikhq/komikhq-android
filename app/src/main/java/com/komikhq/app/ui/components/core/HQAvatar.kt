package com.komikhq.app.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

// ============================================================
// HQAvatar — KomikHQ Design System Avatar Primitive
//
// Usage:
//   HQAvatar()                                           // default person icon
//   HQAvatar(imageUrl = user.avatarUrl)                  // remote image
//   HQAvatar(size = HQAvatarSize.LG)                    // large variant
// ============================================================

enum class HQAvatarSize(val diameter: Dp, val iconSize: Dp) {
    SM(diameter = 32.dp, iconSize = 18.dp),
    MD(diameter = 48.dp, iconSize = 28.dp),
    LG(diameter = 60.dp, iconSize = 36.dp),
    XL(diameter = 80.dp, iconSize = 48.dp)
}

@Composable
fun HQAvatar(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    size: HQAvatarSize = HQAvatarSize.LG,
    fallbackIcon: ImageVector = Icons.Default.Person,
    contentDescription: String = "Avatar"
) {
    if (!imageUrl.isNullOrBlank()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size.diameter)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    } else {
        Box(
            modifier = modifier
                .size(size.diameter)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = fallbackIcon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(size.iconSize)
            )
        }
    }
}


package com.komikhq.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQAvatar
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.feature.settings.SettingsScaffold

/**
 * KHQ 24 · Avatar Picker Screen
 * Grid of preset avatars + upload photo option
 */
@Composable
fun AvatarPickerScreen(
    currentAvatarUrl: String?,
    onAvatarSelected: (String) -> Unit,
    onUploadPhoto: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Preset avatar URLs (would come from SDK/config in production)
    val presetAvatars = remember {
        (1..12).map { "https://api.komikhq.com/avatars/preset-$it.png" }
    }
    var selectedAvatar by remember { mutableStateOf(currentAvatarUrl) }

    SettingsScaffold(
        title = stringResource(R.string.profile_avatar_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        // Upload button
        HQButton(
            text = stringResource(R.string.profile_avatar_upload),
            onClick = onUploadPhoto,
            variant = HQButtonVariant.OUTLINE,
            size = HQButtonSize.MD,
            fullWidth = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        BasicText(
            text = stringResource(R.string.profile_avatar_title),
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = HQTheme.colors.text
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Preset avatar grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(presetAvatars) { avatarUrl ->
                val isSelected = selectedAvatar == avatarUrl
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .then(
                            if (isSelected) Modifier.border(3.dp, HQOrange, CircleShape)
                            else Modifier
                        )
                        .clickable {
                            selectedAvatar = avatarUrl
                            onAvatarSelected(avatarUrl)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    HQAvatar(
                        avatarUrl = avatarUrl,
                        displayName = "",
                        size = 64.dp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        HQButton(
            text = stringResource(R.string.action_save),
            onClick = { selectedAvatar?.let { onAvatarSelected(it) } },
            size = HQButtonSize.LG,
            fullWidth = true
        )
    }
}


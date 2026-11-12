package com.komikhq.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQAvatar
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQTextField

/**
 * KHQ 23 · Edit Profile Screen
 */
@Composable
fun EditProfileScreen(
    currentName: String,
    currentUsername: String,
    currentBio: String,
    avatarUrl: String?,
    onSave: (name: String, username: String, bio: String) -> Unit,
    onAvatarClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(currentName) }
    var username by remember { mutableStateOf(currentUsername) }
    var bio by remember { mutableStateOf(currentBio) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(HQTheme.colors.surface)
                        .clickable(onClick = onNavigateBack),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.ArrowBack,
                        contentDescription = stringResource(R.string.action_back),
                        tint = HQTheme.colors.text,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                BasicText(
                    text = stringResource(R.string.profile_edit_title),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = HQTheme.colors.text
                    )
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = onAvatarClick)
            ) {
                HQAvatar(avatarUrl = avatarUrl, displayName = currentName, size = 80.dp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.profile_avatar_upload),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = com.komikhq.app.core.designsystem.HQOrange
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Fields
            HQTextField(
                value = name,
                onValueChange = { name = it },
                label = stringResource(R.string.profile_edit_display_name),
                placeholder = stringResource(R.string.auth_placeholder_name),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            HQTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(R.string.profile_edit_username),
                placeholder = "@username",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            HQTextField(
                value = bio,
                onValueChange = { bio = it },
                label = stringResource(R.string.profile_edit_bio),
                placeholder = "",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = false
            )

            Spacer(modifier = Modifier.height(32.dp))

            HQButton(
                text = stringResource(R.string.action_save),
                onClick = { onSave(name, username, bio) },
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}


package com.komikhq.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQAvatar
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQConfirmDialog

/**
 * KHQ 21 · Profile Screen (Authenticated User)
 * KHQ 22 · Guest Profile Screen
 * KHQ 34 · Logout Confirmation Dialog
 */
@Composable
fun ProfileScreen(
    userName: String?,
    userEmail: String?,
    avatarUrl: String?,
    isGuest: Boolean,
    onEditProfile: () -> Unit,
    onAccountInfo: () -> Unit,
    onReadingStats: () -> Unit,
    onReadingPrefs: () -> Unit,
    onSecurity: () -> Unit,
    onNotifications: () -> Unit,
    onTheme: () -> Unit,
    onLanguage: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        HQConfirmDialog(
            title = stringResource(R.string.profile_logout_confirm_title),
            message = stringResource(R.string.profile_logout_confirm_desc),
            confirmText = stringResource(R.string.profile_menu_logout),
            cancelText = stringResource(R.string.action_cancel),
            isDestructive = true,
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

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
            // Header
            BasicText(
                text = stringResource(R.string.profile_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = HQTheme.colors.text
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isGuest) {
                // Guest Profile Card (KHQ 22)
                GuestProfileCard(onSignIn = onSignIn)
            } else {
                // User Profile Card
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HQAvatar(
                        avatarUrl = avatarUrl,
                        displayName = userName ?: "",
                        size = 64.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        BasicText(
                            text = userName ?: "",
                            style = TextStyle(
                                fontFamily = InstrumentSansFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = HQTheme.colors.text
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        BasicText(
                            text = userEmail ?: "",
                            style = TextStyle(
                                fontFamily = InterFamily,
                                fontSize = 13.sp,
                                color = HQTheme.colors.secondaryText
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(HQTheme.colors.surface)
                            .clickable(onClick = onEditProfile),
                        contentAlignment = Alignment.Center
                    ) {
                        HQIcon(
                            imageVector = HQIcons.Settings,
                            contentDescription = stringResource(R.string.profile_edit_title),
                            tint = HQTheme.colors.text,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Menu Sections
            if (!isGuest) {
                ProfileMenuSection(
                    items = listOf(
                        ProfileMenuItem(HQIcons.User, R.string.profile_menu_account_info, onAccountInfo),
                        ProfileMenuItem(HQIcons.Star, R.string.profile_menu_reading_stats, onReadingStats),
                        ProfileMenuItem(HQIcons.Eye, R.string.profile_menu_reading_prefs, onReadingPrefs),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileMenuSection(
                    items = listOf(
                        ProfileMenuItem(HQIcons.Shield, R.string.profile_menu_security, onSecurity),
                        ProfileMenuItem(HQIcons.Bell, R.string.profile_menu_notifications, onNotifications),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            ProfileMenuSection(
                items = listOf(
                    ProfileMenuItem(HQIcons.Brightness, R.string.profile_menu_theme, onTheme),
                    ProfileMenuItem(HQIcons.Settings, R.string.profile_menu_language, onLanguage),
                    ProfileMenuItem(HQIcons.CheckCircle, R.string.profile_menu_about, onAbout),
                )
            )

            if (!isGuest) {
                Spacer(modifier = Modifier.height(16.dp))

                // Logout
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(HQTheme.colors.surface)
                        .clickable { showLogoutDialog = true }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HQIcon(
                        imageVector = HQIcons.LogOut,
                        contentDescription = null,
                        tint = ErrorRed,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    BasicText(
                        text = stringResource(R.string.profile_menu_logout),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            color = ErrorRed
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun GuestProfileCard(onSignIn: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HQOrange.copy(alpha = 0.08f))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HQIcon(
            imageVector = HQIcons.User,
            contentDescription = null,
            tint = HQOrange,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        BasicText(
            text = stringResource(R.string.profile_guest_title),
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HQTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        BasicText(
            text = stringResource(R.string.profile_guest_desc),
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = HQTheme.colors.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        HQButton(
            text = stringResource(R.string.profile_guest_cta),
            onClick = onSignIn,
            fullWidth = true
        )
    }
}

private data class ProfileMenuItem(
    val icon: ImageVector,
    val labelRes: Int,
    val onClick: () -> Unit
)

@Composable
private fun ProfileMenuSection(items: List<ProfileMenuItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = item.onClick)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HQIcon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = HQTheme.colors.text,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                BasicText(
                    text = stringResource(item.labelRes),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = HQTheme.colors.text
                    ),
                    modifier = Modifier.weight(1f)
                )
                HQIcon(
                    imageVector = HQIcons.ChevronRight,
                    contentDescription = null,
                    tint = HQTheme.colors.muted,
                    modifier = Modifier.size(18.dp)
                )
            }
            if (index < items.lastIndex) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(1.dp)
                        .background(HQTheme.colors.border)
                )
            }
        }
    }
}


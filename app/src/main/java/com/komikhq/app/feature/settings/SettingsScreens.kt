package com.komikhq.app.feature.settings

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily

/**
 * KHQ 25 · Account Info Screen
 */
@Composable
fun AccountInfoScreen(
    name: String,
    email: String,
    role: String,
    joinedDate: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_menu_account_info),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        InfoRow(label = stringResource(R.string.profile_edit_display_name), value = name)
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = stringResource(R.string.auth_label_email), value = email)
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = stringResource(R.string.profile_account_role), value = role)
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = stringResource(R.string.profile_account_joined), value = joinedDate)
    }
}

/**
 * KHQ 26 · Reading Stats Screen
 */
@Composable
fun ReadingStatsScreen(
    chaptersRead: Int,
    readingStreak: Int,
    favoriteGenres: List<String>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_stats_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        // Stats cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                value = chaptersRead.toString(),
                label = stringResource(R.string.profile_chapters_read, chaptersRead),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = readingStreak.toString(),
                label = stringResource(R.string.profile_reading_streak, readingStreak),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        BasicText(
            text = stringResource(R.string.profile_stats_favorite_genres),
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = HQTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        favoriteGenres.forEach { genre ->
            BasicText(
                text = "• $genre",
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    color = HQTheme.colors.secondaryText
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

/**
 * KHQ 27 · Reading Preferences Screen
 */
@Composable
fun ReadingPreferencesScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_menu_reading_prefs),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        SettingsToggleRow(
            label = stringResource(R.string.profile_prefs_tap_to_scroll),
            checked = true,
            onCheckedChange = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        SettingsToggleRow(
            label = stringResource(R.string.profile_prefs_volume_nav),
            checked = false,
            onCheckedChange = {}
        )
    }
}

/**
 * KHQ 28 · Change Password Screen
 */
@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit,
    onSave: (currentPassword: String, newPassword: String) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_change_password_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        com.komikhq.app.core.ui.HQTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.profile_current_password),
            placeholder = "",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        com.komikhq.app.core.ui.HQTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.auth_new_password_label),
            placeholder = stringResource(R.string.auth_placeholder_password),
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        com.komikhq.app.core.ui.HQTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.auth_confirm_new_password_label),
            placeholder = stringResource(R.string.auth_placeholder_confirm_password),
            isPassword = true
        )
        Spacer(modifier = Modifier.height(32.dp))
        com.komikhq.app.core.ui.HQButton(
            text = stringResource(R.string.action_save),
            onClick = { onSave("", "") },
            size = com.komikhq.app.core.ui.HQButtonSize.LG,
            fullWidth = true
        )
    }
}

/**
 * KHQ 29 · Privacy & Data Screen
 */
@Composable
fun PrivacyScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_privacy_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        SettingsToggleRow(
            label = stringResource(R.string.profile_privacy_track_history),
            checked = true,
            onCheckedChange = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        SettingsToggleRow(
            label = stringResource(R.string.profile_privacy_public_profile),
            checked = false,
            onCheckedChange = {}
        )
    }
}

/**
 * KHQ 30 · Security Screen
 */
@Composable
fun SecurityScreen(
    onNavigateBack: () -> Unit,
    onChangePassword: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_menu_security),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        SettingsToggleRow(
            label = stringResource(R.string.profile_security_2fa),
            checked = false,
            onCheckedChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsNavRow(
            label = stringResource(R.string.profile_security_sessions),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsNavRow(
            label = stringResource(R.string.profile_change_password_title),
            onClick = onChangePassword
        )
        Spacer(modifier = Modifier.height(24.dp))
        com.komikhq.app.core.ui.HQButton(
            text = stringResource(R.string.profile_delete_account_title),
            onClick = onDeleteAccount,
            variant = com.komikhq.app.core.ui.HQButtonVariant.DESTRUCTIVE,
            size = com.komikhq.app.core.ui.HQButtonSize.MD,
            fullWidth = true
        )
    }
}

/**
 * KHQ 31 · Notifications Settings Screen
 */
@Composable
fun NotificationSettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_menu_notifications),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        SettingsToggleRow(
            label = stringResource(R.string.profile_notif_new_chapter),
            checked = true,
            onCheckedChange = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        SettingsToggleRow(
            label = stringResource(R.string.profile_notif_recommendations),
            checked = false,
            onCheckedChange = {}
        )
    }
}

/**
 * KHQ 32 · Theme Selection Screen
 */
@Composable
fun ThemeScreen(
    currentTheme: String,
    onThemeSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_theme_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        val themes = listOf(
            "system" to R.string.profile_theme_system,
            "dark" to R.string.profile_theme_dark,
            "light" to R.string.profile_theme_light
        )
        themes.forEach { (key, labelRes) ->
            ThemeOptionRow(
                label = stringResource(labelRes),
                selected = currentTheme == key,
                onClick = { onThemeSelected(key) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/**
 * KHQ 33 · Language Selection Screen
 */
@Composable
fun LanguageScreen(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_lang_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        val languages = listOf(
            "id" to R.string.profile_lang_id,
            "en" to R.string.profile_lang_en
        )
        languages.forEach { (key, labelRes) ->
            ThemeOptionRow(
                label = stringResource(labelRes),
                selected = currentLanguage == key,
                onClick = { onLanguageSelected(key) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/**
 * KHQ 35 · Delete Account Screen
 */
@Composable
fun DeleteAccountScreen(
    onConfirmDelete: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.profile_delete_account_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        BasicText(
            text = stringResource(R.string.profile_delete_account_desc),
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = HQTheme.colors.secondaryText
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        com.komikhq.app.core.ui.HQTextField(
            value = "",
            onValueChange = {},
            label = "",
            placeholder = stringResource(R.string.profile_delete_account_confirm_hint)
        )

        Spacer(modifier = Modifier.height(32.dp))

        com.komikhq.app.core.ui.HQButton(
            text = stringResource(R.string.action_delete),
            onClick = onConfirmDelete,
            variant = com.komikhq.app.core.ui.HQButtonVariant.DESTRUCTIVE,
            size = com.komikhq.app.core.ui.HQButtonSize.LG,
            fullWidth = true
        )
    }
}

/**
 * KHQ 36 · Account Deleted Confirmation
 */
@Composable
fun AccountDeletedScreen(
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HQIcon(
                imageVector = HQIcons.CheckCircle,
                contentDescription = null,
                tint = HQTheme.colors.muted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            BasicText(
                text = stringResource(R.string.profile_deleted_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = HQTheme.colors.text
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicText(
                text = stringResource(R.string.profile_deleted_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            com.komikhq.app.core.ui.HQButton(
                text = stringResource(R.string.action_done),
                onClick = onExit,
                size = com.komikhq.app.core.ui.HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}

// ─── Shared Setting Primitives ──────────────────────────

@Composable
fun SettingsScaffold(
    title: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = title,
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = HQTheme.colors.text
                    )
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column {
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 12.sp,
                color = HQTheme.colors.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = value,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = HQTheme.colors.text
            )
        )
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = value,
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = com.komikhq.app.core.designsystem.HQOrange
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 12.sp,
                color = HQTheme.colors.secondaryText
            )
        )
    }
}

@Composable
fun SettingsToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = HQTheme.colors.text
            ),
            modifier = Modifier.weight(1f)
        )
        // Custom toggle indicator
        Box(
            modifier = Modifier
                .size(width = 44.dp, height = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (checked) com.komikhq.app.core.designsystem.HQOrange
                    else HQTheme.colors.border
                )
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(androidx.compose.ui.graphics.Color.White)
                    .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun SettingsNavRow(
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = label,
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
}

@Composable
private fun ThemeOptionRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) com.komikhq.app.core.designsystem.HQOrange.copy(alpha = 0.08f) else HQTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFamily,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 15.sp,
                color = if (selected) com.komikhq.app.core.designsystem.HQOrange else HQTheme.colors.text
            ),
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            HQIcon(
                imageVector = HQIcons.CheckCircle,
                contentDescription = null,
                tint = com.komikhq.app.core.designsystem.HQOrange,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


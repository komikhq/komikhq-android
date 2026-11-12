package com.komikhq.app.feature.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.designsystem.WarningYellow
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQCircularProgressIndicator

/**
 * KHQ 46 · Loading State Screen
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HQCircularProgressIndicator(
                color = HQOrange,
                size = 48.dp,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            BasicText(
                text = stringResource(R.string.loading_label),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    color = HQTheme.colors.secondaryText
                )
            )
        }
    }
}

/**
 * KHQ 49 · Offline State Screen
 */
@Composable
fun OfflineScreen(
    onRetry: () -> Unit,
    onOpenDownloads: () -> Unit,
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HQIcon(
                imageVector = HQIcons.Download,
                contentDescription = null,
                tint = WarningYellow,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicText(
                text = stringResource(R.string.offline_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.offline_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            HQButton(
                text = stringResource(R.string.action_retry),
                onClick = onRetry,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            HQButton(
                text = stringResource(R.string.offline_cta_downloads),
                onClick = onOpenDownloads,
                variant = HQButtonVariant.OUTLINE,
                size = HQButtonSize.MD,
                fullWidth = true
            )
        }
    }
}

/**
 * KHQ 50 · Error Boundary Screen
 */
@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HQIcon(
                imageVector = HQIcons.Close,
                contentDescription = null,
                tint = com.komikhq.app.core.designsystem.ErrorRed,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicText(
                text = stringResource(R.string.error_boundary_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.error_boundary_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            HQButton(
                text = stringResource(R.string.action_retry),
                onClick = onRetry,
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}

/**
 * KHQ 51 · Notifications Center Screen
 */
@Composable
fun NotificationsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    com.komikhq.app.feature.settings.SettingsScaffold(
        title = stringResource(R.string.notifications_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        // Empty state for notifications
        com.komikhq.app.core.ui.HQEmptyState(
            title = stringResource(R.string.notifications_title),
            description = stringResource(R.string.notifications_empty),
            icon = HQIcons.Bell
        )
    }
}

/**
 * KHQ 52 · Notification Permission Request Screen
 */
@Composable
fun NotificationPermissionScreen(
    onAllow: () -> Unit,
    onDismiss: () -> Unit,
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HQIcon(
                imageVector = HQIcons.Bell,
                contentDescription = null,
                tint = HQOrange,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicText(
                text = stringResource(R.string.permission_notif_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.permission_notif_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            HQButton(
                text = stringResource(R.string.permission_notif_cta),
                onClick = onAllow,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            HQButton(
                text = stringResource(R.string.permission_notif_dismiss),
                onClick = onDismiss,
                variant = HQButtonVariant.GHOST,
                size = HQButtonSize.MD,
                fullWidth = true
            )
        }
    }
}


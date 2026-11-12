package com.komikhq.app.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant

/**
 * KHQ 12 · Auth Error Dialog
 * Custom overlay dialog with error icon, title, description, retry/dismiss CTAs
 */
@Composable
fun AuthErrorDialog(
    errorMessage: String?,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(HQTheme.colors.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Error icon
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(ErrorRed.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.Close,
                    contentDescription = null,
                    tint = ErrorRed,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Error headline
            BasicText(
                text = stringResource(R.string.auth_error_headline),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Error description
            BasicText(
                text = errorMessage ?: stringResource(R.string.auth_error_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Retry button
            HQButton(
                text = stringResource(R.string.action_retry),
                onClick = onRetry,
                size = HQButtonSize.MD,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Dismiss button
            HQButton(
                text = stringResource(R.string.action_close),
                onClick = onDismiss,
                variant = HQButtonVariant.GHOST,
                size = HQButtonSize.MD,
                fullWidth = true
            )
        }
    }
}

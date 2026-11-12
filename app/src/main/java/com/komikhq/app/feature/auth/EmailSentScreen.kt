package com.komikhq.app.feature.auth

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import kotlinx.coroutines.delay

/**
 * KHQ 08 · Email Sent Screen
 */
@Composable
fun EmailSentScreen(
    email: String,
    onResend: () -> Unit,
    onBackToSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var countdown by remember { mutableIntStateOf(60) }

    LaunchedEffect(countdown) {
        if (countdown > 0) {
            delay(1000L)
            countdown--
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(HQOrange.copy(alpha = 0.15f))
                    .border(1.dp, HQOrange.copy(alpha = 0.3f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.Bell,
                    contentDescription = null,
                    tint = HQOrange,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = stringResource(R.string.auth_email_sent_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            BasicText(
                text = stringResource(R.string.auth_email_sent_subtitle, email),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            HQButton(
                text = stringResource(R.string.auth_open_email_app),
                onClick = {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_EMAIL)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (_: Exception) {}
                },
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (countdown > 0) {
                HQButton(
                    text = stringResource(R.string.auth_resend_email, countdown),
                    onClick = {},
                    enabled = false,
                    variant = HQButtonVariant.GHOST,
                    size = HQButtonSize.MD,
                    fullWidth = true
                )
            } else {
                HQButton(
                    text = stringResource(R.string.auth_resend_now),
                    onClick = {
                        countdown = 60
                        onResend()
                    },
                    variant = HQButtonVariant.GHOST,
                    size = HQButtonSize.MD,
                    fullWidth = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            HQButton(
                text = stringResource(R.string.auth_link_sign_in),
                onClick = onBackToSignIn,
                variant = HQButtonVariant.LINK,
                size = HQButtonSize.SM,
                fullWidth = true
            )
        }
    }
}


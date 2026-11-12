package com.komikhq.app.feature.auth

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.designsystem.SuccessGreen
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize

/**
 * KHQ 11 · Auth Success Screen
 * Large checkmark icon + success headline + continue CTA
 */
@Composable
fun AuthSuccessScreen(
    onContinue: () -> Unit,
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
            // Large Check Circle
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(SuccessGreen.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Headline
            BasicText(
                text = stringResource(R.string.auth_success_headline),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    lineHeight = 34.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            BasicText(
                text = stringResource(R.string.auth_success_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Continue CTA
            HQButton(
                text = stringResource(R.string.action_continue),
                onClick = onContinue,
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}


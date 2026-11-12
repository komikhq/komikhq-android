package com.komikhq.app.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

/**
 * KHQ 04 · Guest Access Screen
 * Compares guest access vs full account benefits.
 */
@Composable
fun GuestAccessScreen(
    onRegister: () -> Unit,
    onContinueGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(HQOrange.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.User,
                    contentDescription = null,
                    tint = HQOrange,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            BasicText(
                text = stringResource(R.string.onboarding_guest_title),
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
                text = stringResource(R.string.onboarding_guest_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Benefit cards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(HQTheme.colors.surface)
                    .border(1.dp, HQTheme.colors.border, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                GuestBenefitItem(text = stringResource(R.string.onboarding_guest_benefit1))
                Spacer(modifier = Modifier.height(12.dp))
                GuestBenefitItem(text = stringResource(R.string.onboarding_guest_benefit2))
                Spacer(modifier = Modifier.height(12.dp))
                GuestLimitationItem(text = stringResource(R.string.onboarding_guest_limitation))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            HQButton(
                text = stringResource(R.string.onboarding_guest_cta_register),
                onClick = onRegister,
                size = HQButtonSize.LG,
                variant = HQButtonVariant.PRIMARY,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            HQButton(
                text = stringResource(R.string.onboarding_guest_cta_continue),
                onClick = onContinueGuest,
                size = HQButtonSize.MD,
                variant = HQButtonVariant.GHOST,
                fullWidth = true
            )
        }
    }
}

@Composable
private fun GuestBenefitItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HQIcon(
            imageVector = HQIcons.CheckCircle,
            contentDescription = null,
            tint = HQOrange,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicText(
            text = text,
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 13.sp,
                color = HQTheme.colors.text
            )
        )
    }
}

@Composable
private fun GuestLimitationItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HQIcon(
            imageVector = HQIcons.Shield,
            contentDescription = null,
            tint = HQTheme.colors.muted,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicText(
            text = text,
            style = TextStyle(
                fontFamily = InterFamily,
                fontSize = 13.sp,
                color = HQTheme.colors.secondaryText
            )
        )
    }
}


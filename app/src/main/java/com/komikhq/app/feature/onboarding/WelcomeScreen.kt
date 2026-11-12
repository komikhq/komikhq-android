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
import com.komikhq.app.core.ui.HQLogo
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant

/**
 * KHQ 02 · Welcome Screen
 * Features: Visual introductory preview card, welcome headline, subtitle, primary CTA, and sign in shortcut.
 */
@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onSignIn: () -> Unit,
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
            HQLogo(variant = LogoVariant.FULL, size = LogoSize.LARGE)

            Spacer(modifier = Modifier.height(32.dp))

            // Illustrated reader preview mockup card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(HQTheme.colors.surface)
                    .border(1.dp, HQTheme.colors.border, RoundedCornerShape(20.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(HQOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        HQIcon(
                            imageVector = HQIcons.Library,
                            contentDescription = null,
                            tint = HQOrange,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    BasicText(
                        text = stringResource(R.string.onboarding_welcome_title),
                        style = TextStyle(
                            fontFamily = InstrumentSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = HQTheme.colors.text,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = stringResource(R.string.onboarding_welcome_subtitle),
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
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HQButton(
                text = stringResource(R.string.action_get_started),
                onClick = onGetStarted,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = stringResource(R.string.auth_have_account),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                HQButton(
                    text = stringResource(R.string.auth_link_sign_in),
                    onClick = onSignIn,
                    variant = HQButtonVariant.LINK,
                    fullWidth = false,
                    size = HQButtonSize.SM
                )
            }
        }
    }
}


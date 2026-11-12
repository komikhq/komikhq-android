package com.komikhq.app.feature.onboarding

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
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQLogo
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant

/**
 * KHQ 01 · Splash Screen
 * Features: Large centered Brand Mark, tagline, subtitle, primary CTA "Mulai menjelajah", and footer.
 */
@Composable
fun SplashScreen(
    onStartExploring: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HQLogo(
                variant = LogoVariant.MARK,
                size = LogoSize.XLARGE
            )

            Spacer(modifier = Modifier.height(28.dp))

            HQLogo(
                variant = LogoVariant.WORDMARK,
                size = LogoSize.LARGE
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicText(
                text = stringResource(R.string.brand_tagline),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.brand_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
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
                text = stringResource(R.string.action_start_exploring),
                onClick = onStartExploring,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicText(
                text = stringResource(R.string.brand_footer),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = HQTheme.colors.muted,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}


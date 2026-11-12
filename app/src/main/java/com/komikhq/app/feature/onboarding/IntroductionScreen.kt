package com.komikhq.app.feature.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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

private data class IntroStep(
    val titleRes: Int,
    val descRes: Int,
    val icon: ImageVector
)

/**
 * KHQ 03 · Introduction Screen
 * 3-step feature introduction (Offline downloads, Gapless reader engine, Cloud synchronization).
 * Dot indicators: 1 active pill (18 x 5 dp) + 2 inactive dots (6 x 5 dp).
 */
@Composable
fun IntroductionScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        IntroStep(R.string.onboarding_step1_title, R.string.onboarding_step1_desc, HQIcons.Download),
        IntroStep(R.string.onboarding_step2_title, R.string.onboarding_step2_desc, HQIcons.Compass),
        IntroStep(R.string.onboarding_step3_title, R.string.onboarding_step3_desc, HQIcons.Shield)
    )

    var currentStepIndex by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIndex]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        // Skip Button at top right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.End
        ) {
            HQButton(
                text = stringResource(R.string.action_skip),
                onClick = onFinish,
                variant = HQButtonVariant.GHOST,
                fullWidth = false,
                size = HQButtonSize.SM
            )
        }

        // Center Step Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "IntroContent"
            ) { step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(HQTheme.colors.surface)
                            .border(1.dp, HQTheme.colors.border, RoundedCornerShape(28.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        HQIcon(
                            imageVector = step.icon,
                            contentDescription = null,
                            tint = HQOrange,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    BasicText(
                        text = stringResource(step.titleRes),
                        style = TextStyle(
                            fontFamily = InstrumentSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            lineHeight = 28.sp,
                            color = HQTheme.colors.text,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BasicText(
                        text = stringResource(step.descRes),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = HQTheme.colors.secondaryText,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Carousel dots indicator matching Figma
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                steps.indices.forEach { index ->
                    val isActive = index == currentStepIndex
                    Box(
                        modifier = Modifier
                            .size(width = if (isActive) 18.dp else 6.dp, height = 5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (isActive) HQOrange else HQTheme.colors.muted.copy(alpha = 0.4f))
                    )
                    if (index < steps.size - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }

        // Bottom CTAs
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            HQButton(
                text = if (currentStepIndex == steps.size - 1) {
                    stringResource(R.string.action_done)
                } else {
                    stringResource(R.string.action_next)
                },
                onClick = {
                    if (currentStepIndex < steps.size - 1) {
                        currentStepIndex++
                    } else {
                        onFinish()
                    }
                },
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}


package com.komikhq.app.feature.auth

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
 * KHQ 10 · Email Verification Screen
 * 6-digit OTP input with auto-focus-forward
 */
@Composable
fun EmailVerificationScreen(
    email: String,
    onVerify: (String) -> Unit,
    onResendCode: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val otpDigits = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(6) { FocusRequester() } }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    val otpCode = otpDigits.joinToString("")
    val isComplete = otpCode.length == 6

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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back button
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(HQTheme.colors.surface)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.ArrowBack,
                        contentDescription = stringResource(R.string.action_back),
                        tint = HQTheme.colors.text,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Eyebrow
            BasicText(
                text = stringResource(R.string.auth_verify_email_eyebrow),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = HQOrange,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Headline
            BasicText(
                text = stringResource(R.string.auth_verify_email_headline),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    lineHeight = 34.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.auth_verify_email_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // OTP Input Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                for (i in 0 until 6) {
                    OtpDigitBox(
                        value = otpDigits[i],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                otpDigits[i] = newValue
                                if (newValue.isNotEmpty() && i < 5) {
                                    focusRequesters[i + 1].requestFocus()
                                }
                            } else if (newValue.isEmpty() && i > 0) {
                                otpDigits[i] = ""
                                focusRequesters[i - 1].requestFocus()
                            }
                        },
                        focusRequester = focusRequesters[i],
                        isFocused = otpDigits[i].isEmpty() && otpDigits.take(i).all { it.isNotEmpty() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Verify Button
            HQButton(
                text = stringResource(R.string.auth_btn_verify),
                onClick = { onVerify(otpCode) },
                enabled = isComplete,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Resend Code
            HQButton(
                text = stringResource(R.string.auth_verify_otp_resend),
                onClick = onResendCode,
                variant = HQButtonVariant.GHOST,
                size = HQButtonSize.MD,
                fullWidth = true
            )
        }
    }
}

@Composable
private fun OtpDigitBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (value.isNotEmpty()) HQOrange else HQTheme.colors.border

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .size(width = 48.dp, height = 56.dp)
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (value.isNotEmpty()) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(HQTheme.colors.surface),
        textStyle = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = HQTheme.colors.text,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                innerTextField()
            }
        }
    )
}


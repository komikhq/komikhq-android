package com.komikhq.app.feature.auth

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.designsystem.SuccessGreen
import com.komikhq.app.core.designsystem.WarningYellow
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQTextField

/**
 * KHQ 09 · Reset Password Screen
 * New password + confirm password + strength meter bar
 */
@Composable
fun ResetPasswordScreen(
    onPasswordReset: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val strengthLevel = calculatePasswordStrength(newPassword)
    val passwordsMatch = newPassword == confirmPassword && confirmPassword.isNotEmpty()
    val canSubmit = strengthLevel >= 2 && passwordsMatch

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
            // Back button
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

            Spacer(modifier = Modifier.height(32.dp))

            // Eyebrow
            BasicText(
                text = stringResource(R.string.auth_reset_password_eyebrow),
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
                text = stringResource(R.string.auth_reset_password_headline),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    lineHeight = 34.sp,
                    color = HQTheme.colors.text
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.auth_reset_password_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // New Password
            HQTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = stringResource(R.string.auth_new_password_label),
                placeholder = stringResource(R.string.auth_placeholder_password),
                isPassword = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Strength Meter
            PasswordStrengthMeter(strengthLevel = strengthLevel)

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            HQTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(R.string.auth_confirm_new_password_label),
                placeholder = stringResource(R.string.auth_placeholder_confirm_password),
                isPassword = true,
                errorMessage = if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                    stringResource(R.string.auth_error_password_mismatch)
                } else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            HQButton(
                text = stringResource(R.string.auth_btn_reset_password),
                onClick = onPasswordReset,
                enabled = canSubmit,
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}

@Composable
private fun PasswordStrengthMeter(strengthLevel: Int) {
    val colors = listOf(
        if (strengthLevel >= 1) ErrorRed else HQTheme.colors.surface,
        if (strengthLevel >= 2) HQOrange else HQTheme.colors.surface,
        if (strengthLevel >= 3) WarningYellow else HQTheme.colors.surface,
        if (strengthLevel >= 4) SuccessGreen else HQTheme.colors.surface
    )

    val strengthLabels = listOf(
        R.string.auth_strength_weak,
        R.string.auth_strength_fair,
        R.string.auth_strength_good,
        R.string.auth_strength_strong
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                )
            }
        }

        if (strengthLevel > 0) {
            Spacer(modifier = Modifier.height(6.dp))
            BasicText(
                text = stringResource(strengthLabels[strengthLevel - 1]),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = colors[strengthLevel - 1]
                )
            )
        }
    }
}

private fun calculatePasswordStrength(password: String): Int {
    if (password.isEmpty()) return 0
    var score = 0
    if (password.length >= 8) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++
    if (password.length >= 12 && password.any { it.isUpperCase() }) score++
    return score
}


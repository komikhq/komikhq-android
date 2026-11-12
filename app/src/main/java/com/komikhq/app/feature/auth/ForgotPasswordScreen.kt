package com.komikhq.app.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQTextField

/**
 * KHQ 07 · Forgot Password Screen
 */
@Composable
fun ForgotPasswordScreen(
    onSendResetLink: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HQTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Back button
            Row(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onBack),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HQIcon(
                    imageVector = HQIcons.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                    tint = HQTheme.colors.text
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = stringResource(R.string.auth_forgot_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    lineHeight = 34.sp,
                    color = HQTheme.colors.text
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            BasicText(
                text = stringResource(R.string.auth_forgot_subtitle),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = HQTheme.colors.secondaryText
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            HQTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.auth_label_email),
                placeholder = stringResource(R.string.auth_placeholder_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(24.dp))

            HQButton(
                text = stringResource(R.string.auth_forgot_send_btn),
                onClick = {
                    if (email.isNotBlank()) {
                        isLoading = true
                        onSendResetLink(email.trim())
                    }
                },
                enabled = email.isNotBlank(),
                isLoading = isLoading,
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}


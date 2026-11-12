package com.komikhq.app.feature.auth

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQLogo
import com.komikhq.app.core.ui.HQTextField
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant
import com.komikhq.app.ui.auth.AuthUiState
import com.komikhq.app.ui.auth.AuthViewModel

/**
 * KHQ 05 · Sign In Screen (& KHQ 58 · Password Visible State)
 */
@Composable
fun SignInScreen(
    viewModel: AuthViewModel,
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onContinueAsGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onSignInSuccess()
        }
    }

    val isLoading = uiState is AuthUiState.Loading
    val errorMessage = (uiState as? AuthUiState.Error)?.message

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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top App Bar / Logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                HQLogo(variant = LogoVariant.FULL, size = LogoSize.HEADER)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Eyebrow & Headline
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicText(
                    text = stringResource(R.string.auth_welcome_back_eyebrow),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = HQOrange,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                BasicText(
                    text = stringResource(R.string.auth_sign_in_headline),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        lineHeight = 34.sp,
                        color = HQTheme.colors.text
                    )
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Email Field
            HQTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (uiState is AuthUiState.Error) viewModel.resetState()
                },
                label = stringResource(R.string.auth_label_email),
                placeholder = stringResource(R.string.auth_placeholder_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field (with toggle eye icon - KHQ 58)
            HQTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (uiState is AuthUiState.Error) viewModel.resetState()
                },
                label = stringResource(R.string.auth_label_password),
                placeholder = stringResource(R.string.auth_placeholder_password),
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )

            // Forgot Password link
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                BasicText(
                    text = stringResource(R.string.auth_link_forgot_password),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = HQOrange
                    ),
                    modifier = Modifier.clickable(onClick = onNavigateToForgotPassword)
                )
            }

            // Error Message
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                BasicText(
                    text = errorMessage,
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 13.sp,
                        color = ErrorRed
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign In Button
            HQButton(
                text = stringResource(R.string.auth_btn_sign_in),
                onClick = { viewModel.signIn(email, password) },
                isLoading = isLoading,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Divider OR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(HQTheme.colors.border))
                Spacer(modifier = Modifier.width(12.dp))
                BasicText(
                    text = stringResource(R.string.auth_divider_or),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp,
                        color = HQTheme.colors.muted
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f).height(1.dp).background(HQTheme.colors.border))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Google One-Tap Sign In
            HQButton(
                text = stringResource(R.string.auth_btn_google),
                onClick = {
                    val activity = context as? Activity
                    if (activity != null) {
                        viewModel.signInWithGoogleNative(activity)
                    }
                },
                variant = HQButtonVariant.OUTLINE,
                size = HQButtonSize.MD,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Guest Bypass
            HQButton(
                text = stringResource(R.string.auth_btn_guest),
                onClick = onContinueAsGuest,
                variant = HQButtonVariant.GHOST,
                size = HQButtonSize.MD,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Don't have an account? Register link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = stringResource(R.string.auth_no_account),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                BasicText(
                    text = stringResource(R.string.auth_link_register),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = HQOrange
                    ),
                    modifier = Modifier.clickable(onClick = onNavigateToSignUp)
                )
            }
        }
    }
}


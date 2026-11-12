package com.komikhq.app.feature.auth

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
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
import com.komikhq.app.core.ui.HQTextField
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant
import com.komikhq.app.ui.auth.AuthUiState
import com.komikhq.app.ui.auth.AuthViewModel

/**
 * KHQ 06 · Sign Up Screen
 * Registration form: Full name, email, password >= 8 chars, password confirmation, terms checkbox, Google One-Tap.
 */
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onSignUpSuccess()
        }
    }

    val isLoading = uiState is AuthUiState.Loading
    val serverError = (uiState as? AuthUiState.Error)?.message
    val displayedError = localError ?: serverError

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                HQLogo(variant = LogoVariant.FULL, size = LogoSize.HEADER)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                BasicText(
                    text = stringResource(R.string.auth_sign_up_headline),
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        lineHeight = 34.sp,
                        color = HQTheme.colors.text
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Full Name Field
            HQTextField(
                value = name,
                onValueChange = {
                    name = it
                    localError = null
                },
                label = stringResource(R.string.auth_label_name),
                placeholder = stringResource(R.string.auth_placeholder_name),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Email Field
            HQTextField(
                value = email,
                onValueChange = {
                    email = it
                    localError = null
                },
                label = stringResource(R.string.auth_label_email),
                placeholder = stringResource(R.string.auth_placeholder_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password Field
            HQTextField(
                value = password,
                onValueChange = {
                    password = it
                    localError = null
                },
                label = stringResource(R.string.auth_label_password),
                placeholder = stringResource(R.string.auth_placeholder_password),
                helperText = stringResource(R.string.auth_helper_password),
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Confirm Password Field
            HQTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    localError = null
                },
                label = stringResource(R.string.auth_label_confirm_password),
                placeholder = stringResource(R.string.auth_placeholder_confirm_password),
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Terms Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { termsAccepted = !termsAccepted },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (termsAccepted) HQOrange else HQTheme.colors.surface)
                        .border(1.dp, if (termsAccepted) HQOrange else HQTheme.colors.border, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (termsAccepted) {
                        HQIcon(
                            imageVector = HQIcons.DownloadDone,
                            contentDescription = null,
                            tint = androidx.compose.ui.graphics.Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                BasicText(
                    text = stringResource(R.string.auth_terms_agreement),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = HQTheme.colors.secondaryText
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            if (displayedError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                BasicText(
                    text = displayedError,
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 13.sp,
                        color = ErrorRed
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up CTA
            HQButton(
                text = stringResource(R.string.auth_btn_sign_up),
                onClick = {
                    if (password != confirmPassword) {
                        localError = context.getString(R.string.auth_label_confirm_password)
                        return@HQButton
                    }
                    if (!termsAccepted) {
                        localError = context.getString(R.string.auth_terms_agreement)
                        return@HQButton
                    }
                    viewModel.signUp(name, email, password)
                },
                isLoading = isLoading,
                size = HQButtonSize.LG,
                fullWidth = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Google One-Tap
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

            Spacer(modifier = Modifier.height(24.dp))

            // Have account? Sign In
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
                BasicText(
                    text = stringResource(R.string.auth_link_sign_in),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = HQOrange
                    ),
                    modifier = Modifier.clickable(onClick = onNavigateToSignIn)
                )
            }
        }
    }
}


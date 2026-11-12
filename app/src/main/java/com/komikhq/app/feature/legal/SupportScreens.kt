package com.komikhq.app.feature.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.komikhq.app.core.ui.HQTextField
import com.komikhq.app.feature.settings.SettingsScaffold

/**
 * KHQ 43 · Contact Us Screen
 */
@Composable
fun ContactScreen(
    onNavigateBack: () -> Unit,
    onSubmitSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.support_contact_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        BasicText(
            text = stringResource(R.string.support_contact_title),
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HQTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        HQButton(
            text = stringResource(R.string.support_form_title),
            onClick = onSubmitSupport,
            size = HQButtonSize.LG,
            fullWidth = true
        )
    }
}

/**
 * KHQ 44 · Support Form Screen
 */
@Composable
fun SupportFormScreen(
    onNavigateBack: () -> Unit,
    onSubmit: (subject: String, message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    SettingsScaffold(
        title = stringResource(R.string.support_form_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        HQTextField(
            value = subject,
            onValueChange = { subject = it },
            label = stringResource(R.string.support_subject_label),
            placeholder = "",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HQTextField(
            value = message,
            onValueChange = { message = it },
            label = stringResource(R.string.support_message_label),
            placeholder = stringResource(R.string.support_message_placeholder),
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(32.dp))

        HQButton(
            text = stringResource(R.string.support_submit_btn),
            onClick = { onSubmit(subject, message) },
            enabled = subject.isNotBlank() && message.isNotBlank(),
            size = HQButtonSize.LG,
            fullWidth = true
        )
    }
}

/**
 * KHQ 45 · Support Sent Screen
 */
@Composable
fun SupportSentScreen(
    onDone: () -> Unit,
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
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(SuccessGreen.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = stringResource(R.string.support_sent_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = HQTheme.colors.text,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicText(
                text = stringResource(R.string.support_sent_desc),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = HQTheme.colors.secondaryText,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            HQButton(
                text = stringResource(R.string.action_done),
                onClick = onDone,
                size = HQButtonSize.LG,
                fullWidth = true
            )
        }
    }
}


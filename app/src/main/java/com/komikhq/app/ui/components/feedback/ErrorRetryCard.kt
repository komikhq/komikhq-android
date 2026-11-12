package com.komikhq.app.ui.components.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant

@Composable
fun ErrorRetryCard(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BasicText(
                text = message,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 14.sp,
                    color = HQTheme.colors.error,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            HQButton(
                text = stringResource(R.string.action_retry),
                onClick = onRetry,
                variant = HQButtonVariant.PRIMARY,
                size = HQButtonSize.SM,
                fullWidth = false,
                leadingIcon = HQIcons.History
            )
        }
    }
}

package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily

/**
 * 100% Custom Modal Confirmation Dialog with zero Material 3 dependency.
 */
@Composable
fun HQConfirmDialog(
    title: String,
    message: String,
    confirmText: String = stringResource(R.string.action_confirm),
    cancelText: String = stringResource(R.string.action_cancel),
    isDestructive: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        val shape = RoundedCornerShape(20.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(HQTheme.colors.surface)
                .border(1.dp, HQTheme.colors.border, shape)
                .padding(24.dp)
        ) {
            Column {
                BasicText(
                    text = title,
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = HQTheme.colors.text
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                BasicText(
                    text = message,
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HQButton(
                        text = cancelText,
                        onClick = onDismiss,
                        variant = HQButtonVariant.GHOST,
                        fullWidth = false,
                        size = HQButtonSize.SM
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    HQButton(
                        text = confirmText,
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        variant = if (isDestructive) HQButtonVariant.DESTRUCTIVE else HQButtonVariant.PRIMARY,
                        fullWidth = false,
                        size = HQButtonSize.SM
                    )
                }
            }
        }
    }
}


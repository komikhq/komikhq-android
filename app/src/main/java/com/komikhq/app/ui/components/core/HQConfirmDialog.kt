package com.komikhq.app.ui.components.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.komikhq.app.R

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
    com.komikhq.app.core.ui.HQConfirmDialog(
        title = title,
        message = message,
        confirmText = confirmText,
        cancelText = cancelText,
        isDestructive = isDestructive,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}

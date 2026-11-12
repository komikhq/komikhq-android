package com.komikhq.app.ui.components.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

// ============================================================
// HQConfirmDialog — KomikHQ Design System Confirmation Dialog
//
// Usage:
//   HQConfirmDialog(
//       title = "Sign Out",
//       message = "Are you sure you want to sign out?",
//       confirmText = "Sign Out",
//       isDestructive = true,
//       onConfirm = { ... },
//       onDismiss = { showDialog = false }
//   )
// ============================================================

@Composable
fun HQConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    isDestructive: Boolean = false
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            if (isDestructive) {
                HQButton(
                    text = confirmText,
                    onClick = onConfirm,
                    variant = HQButtonVariant.DESTRUCTIVE,
                    size = HQButtonSize.SM,
                    fullWidth = false
                )
            } else {
                HQButton(
                    text = confirmText,
                    onClick = onConfirm,
                    variant = HQButtonVariant.PRIMARY,
                    size = HQButtonSize.SM,
                    fullWidth = false
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        }
    )
}

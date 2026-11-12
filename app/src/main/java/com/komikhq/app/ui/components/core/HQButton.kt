package com.komikhq.app.ui.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// HQButton — KomikHQ Design System Button Primitive
//
// Usage:
//   HQButton("Sign In", onClick = { ... })
//   HQButton("Sign In", variant = HQButtonVariant.PRIMARY, size = HQButtonSize.LG, isLoading = true, onClick = { ... })
//   HQButton("Sign Out", variant = HQButtonVariant.DESTRUCTIVE, onClick = { ... })
//   HQButton("Continue with Google", variant = HQButtonVariant.OUTLINE, leadingIcon = Icons.Default.Google, onClick = { ... })
//   HQButton("Cancel", variant = HQButtonVariant.GHOST, onClick = { ... })
// ============================================================

enum class HQButtonVariant {
    PRIMARY,
    SECONDARY,
    DESTRUCTIVE,
    OUTLINE,
    GHOST,
    LINK
}

enum class HQButtonSize(val height: Dp, val fontSize: TextUnit, val iconSize: Dp, val horizontalPadding: Dp) {
    SM(height = 36.dp, fontSize = 12.sp, iconSize = 16.dp, horizontalPadding = 12.dp),
    MD(height = 48.dp, fontSize = 14.sp, iconSize = 18.dp, horizontalPadding = 16.dp),
    LG(height = 54.dp, fontSize = 15.sp, iconSize = 20.dp, horizontalPadding = 24.dp)
}

@Composable
fun HQButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: HQButtonVariant = HQButtonVariant.PRIMARY,
    size: HQButtonSize = HQButtonSize.MD,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    fullWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) {
    val effectiveEnabled = enabled && !isLoading
    val shape = RoundedCornerShape(12.dp)
    val buttonModifier = if (fullWidth) modifier.fillMaxWidth().height(size.height) else modifier.height(size.height)
    val contentPadding = PaddingValues(horizontal = size.horizontalPadding)

    when (variant) {
        HQButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = buttonModifier,
                shape = shape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.onPrimary)
            }
        }

        HQButtonVariant.SECONDARY -> {
            Button(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = buttonModifier,
                shape = shape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }

        HQButtonVariant.DESTRUCTIVE -> {
            Button(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = buttonModifier,
                shape = shape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.onError.copy(alpha = 0.7f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.onError)
            }
        }

        HQButtonVariant.OUTLINE -> {
            OutlinedButton(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = buttonModifier,
                shape = shape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (effectiveEnabled) MaterialTheme.colorScheme.outline
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.onSurface)
            }
        }

        HQButtonVariant.GHOST -> {
            TextButton(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = buttonModifier,
                shape = shape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.onSurface)
            }
        }

        HQButtonVariant.LINK -> {
            TextButton(
                onClick = onClick,
                enabled = effectiveEnabled,
                modifier = if (fullWidth) modifier.fillMaxWidth() else modifier,
                shape = shape,
                contentPadding = PaddingValues(horizontal = 4.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            ) {
                HQButtonContent(text, size, isLoading, leadingIcon, trailingIcon, MaterialTheme.colorScheme.primary)
            }
        }
    }
}

/**
 * Variant-specific destructive outline button (e.g. Sign Out in Profile).
 */
@Composable
fun HQDestructiveOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: HQButtonSize = HQButtonSize.MD,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    fullWidth: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    val effectiveEnabled = enabled && !isLoading
    val shape = RoundedCornerShape(12.dp)
    val buttonModifier = if (fullWidth) modifier.fillMaxWidth().height(size.height) else modifier.height(size.height)

    OutlinedButton(
        onClick = onClick,
        enabled = effectiveEnabled,
        modifier = buttonModifier,
        shape = shape,
        contentPadding = PaddingValues(horizontal = size.horizontalPadding),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.error.copy(alpha = if (effectiveEnabled) 0.5f else 0.25f)
        )
    ) {
        HQButtonContent(text, size, isLoading, leadingIcon, null, MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun HQButtonContent(
    text: String,
    size: HQButtonSize,
    isLoading: Boolean,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
    tintColor: Color
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(size.iconSize + 2.dp),
            color = tintColor,
            strokeWidth = 2.dp
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(size.iconSize),
                    tint = tintColor
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = size.fontSize
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(size.iconSize),
                    tint = tintColor
                )
            }
        }
    }
}

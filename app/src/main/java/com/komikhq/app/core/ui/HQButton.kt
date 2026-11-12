package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

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

/**
 * Custom Compose button primitive built directly from Box & Row without Material UI constraints.
 */
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
    val interactionSource = remember { MutableInteractionSource() }

    val (bgColor, contentColor, borderColor) = when (variant) {
        HQButtonVariant.PRIMARY -> Triple(
            if (effectiveEnabled) HQOrange else HQOrange.copy(alpha = 0.5f),
            Color.White,
            null
        )
        HQButtonVariant.SECONDARY -> Triple(
            if (effectiveEnabled) HQTheme.colors.surface else HQTheme.colors.surface.copy(alpha = 0.5f),
            if (effectiveEnabled) HQTheme.colors.text else HQTheme.colors.text.copy(alpha = 0.5f),
            null
        )
        HQButtonVariant.DESTRUCTIVE -> Triple(
            if (effectiveEnabled) ErrorRed else ErrorRed.copy(alpha = 0.5f),
            Color.White,
            null
        )
        HQButtonVariant.OUTLINE -> Triple(
            HQTheme.colors.background,
            if (effectiveEnabled) HQTheme.colors.text else HQTheme.colors.text.copy(alpha = 0.5f),
            if (effectiveEnabled) HQTheme.colors.border else HQTheme.colors.border.copy(alpha = 0.5f)
        )
        HQButtonVariant.GHOST -> Triple(
            Color.Transparent,
            if (effectiveEnabled) HQTheme.colors.text else HQTheme.colors.text.copy(alpha = 0.5f),
            null
        )
        HQButtonVariant.LINK -> Triple(
            Color.Transparent,
            if (effectiveEnabled) HQOrange else HQOrange.copy(alpha = 0.5f),
            null
        )
    }

    val buttonModifier = if (fullWidth) modifier.fillMaxWidth() else modifier

    Box(
        modifier = buttonModifier
            .height(size.height)
            .clip(shape)
            .background(bgColor)
            .then(
                if (borderColor != null) Modifier.border(1.dp, borderColor, shape)
                else Modifier
            )
            .then(
                if (effectiveEnabled) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                } else Modifier
            )
            .padding(horizontal = size.horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            HQCircularProgressIndicator(
                color = contentColor,
                size = size.iconSize + 2.dp,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (leadingIcon != null) {
                    HQIcon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(size.iconSize)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                BasicText(
                    text = text,
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = size.fontSize,
                        color = contentColor
                    )
                )
                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    HQIcon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(size.iconSize)
                    )
                }
            }
        }
    }
}

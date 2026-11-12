package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.ErrorRed
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

/**
 * Custom Input TextField matching Figma specs with zero Material 3 dependency.
 */
@Composable
fun HQTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: ImageVector? = null,
    errorMessage: String? = null,
    helperText: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(12.dp)
    val hasError = errorMessage != null

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            BasicText(
                text = label,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = HQTheme.colors.text
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(shape)
                .background(HQTheme.colors.surface)
                .border(
                    width = 1.dp,
                    color = if (hasError) ErrorRed else HQTheme.colors.border,
                    shape = shape
                )
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    HQIcon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (hasError) ErrorRed else HQTheme.colors.secondaryText,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        BasicText(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = InterFamily,
                                fontSize = 14.sp,
                                color = HQTheme.colors.muted
                            )
                        )
                    }

                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        singleLine = singleLine,
                        textStyle = TextStyle(
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            color = HQTheme.colors.text
                        ),
                        cursorBrush = SolidColor(HQTheme.colors.primary),
                        visualTransformation = if (isPassword && !passwordVisible) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (isPassword) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { passwordVisible = !passwordVisible },
                        contentAlignment = Alignment.Center
                    ) {
                        HQIcon(
                            imageVector = if (passwordVisible) HQIcons.EyeOff else HQIcons.Eye,
                            contentDescription = stringResource(R.string.auth_label_password),
                            tint = HQTheme.colors.secondaryText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        if (hasError) {
            Spacer(modifier = Modifier.height(4.dp))
            BasicText(
                text = errorMessage,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = ErrorRed
                )
            )
        } else if (helperText != null) {
            Spacer(modifier = Modifier.height(4.dp))
            BasicText(
                text = helperText,
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = HQTheme.colors.muted
                )
            )
        }
    }
}


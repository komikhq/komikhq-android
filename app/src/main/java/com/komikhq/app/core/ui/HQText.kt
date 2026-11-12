package com.komikhq.app.core.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily

/**
 * Custom text composable that renders text using BasicText with design system defaults.
 * Replaces Material 3 Text to avoid Material Theme lookup and font scale opinion overrides.
 */
@Composable
fun HQText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val resolvedColor = if (color != Color.Unspecified) color else if (style.color != Color.Unspecified) style.color else HQTheme.colors.text
    val resolvedFontSize = if (fontSize != TextUnit.Unspecified) fontSize else if (style.fontSize != TextUnit.Unspecified) style.fontSize else 14.sp
    val resolvedFontWeight = fontWeight ?: style.fontWeight ?: FontWeight.Normal
    val resolvedFontFamily = fontFamily ?: style.fontFamily ?: InterFamily

    BasicText(
        text = text,
        modifier = modifier,
        style = style.merge(
            TextStyle(
                color = resolvedColor,
                fontSize = resolvedFontSize,
                fontWeight = resolvedFontWeight,
                fontFamily = resolvedFontFamily,
                lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else style.lineHeight,
                textAlign = if (textAlign != TextAlign.Unspecified) textAlign else style.textAlign
            )
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}


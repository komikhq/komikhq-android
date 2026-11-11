package com.komikhq.app.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.komikhq.app.R
import com.komikhq.app.ui.theme.LocalIsDarkTheme

enum class LogoVariant {
    MARK,
    WORDMARK,
    FULL
}

enum class LogoSize(
    val markSize: Dp,
    val wordmarkWidth: Dp,
    val wordmarkHeight: Dp,
    val spacing: Dp
) {
    COMPACT(markSize = 20.dp, wordmarkWidth = 104.dp, wordmarkHeight = 20.dp, spacing = 6.dp),
    HEADER(markSize = 26.dp, wordmarkWidth = 125.dp, wordmarkHeight = 24.dp, spacing = 8.dp),
    LARGE(markSize = 38.dp, wordmarkWidth = 177.dp, wordmarkHeight = 34.dp, spacing = 10.dp),
    XLARGE(markSize = 52.dp, wordmarkWidth = 240.dp, wordmarkHeight = 46.dp, spacing = 12.dp)
}

/**
 * Standard KomikHQ logo composable matching the web design system
 * (komikhq-web/app/components/common/logo.tsx).
 * Automatically adapts dynamically between dark and light variants based on LocalIsDarkTheme.
 */
@Composable
fun KomikHQLogo(
    modifier: Modifier = Modifier,
    variant: LogoVariant = LogoVariant.FULL,
    size: LogoSize = LogoSize.HEADER,
    isDark: Boolean = LocalIsDarkTheme.current
) {
    val markRes = if (isDark) {
        R.drawable.ic_komikhq_mark_dark
    } else {
        R.drawable.ic_komikhq_mark_light
    }

    val wordmarkRes = if (isDark) {
        R.drawable.ic_komikhq_wordmark_dark
    } else {
        R.drawable.ic_komikhq_wordmark_light
    }

    when (variant) {
        LogoVariant.MARK -> {
            Image(
                painter = painterResource(id = markRes),
                contentDescription = "KomikHQ Mark",
                modifier = modifier.size(size.markSize)
            )
        }
        LogoVariant.WORDMARK -> {
            Image(
                painter = painterResource(id = wordmarkRes),
                contentDescription = "KomikHQ Wordmark",
                modifier = modifier.size(width = size.wordmarkWidth, height = size.wordmarkHeight)
            )
        }
        LogoVariant.FULL -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = markRes),
                    contentDescription = null,
                    modifier = Modifier.size(size.markSize)
                )
                Spacer(modifier = Modifier.width(size.spacing))
                Image(
                    painter = painterResource(id = wordmarkRes),
                    contentDescription = "KomikHQ",
                    modifier = Modifier.size(width = size.wordmarkWidth, height = size.wordmarkHeight)
                )
            }
        }
    }
}

package com.komikhq.app.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.komikhq.app.R

@Composable
fun KomikHQLogo(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    isDark: Boolean = isSystemInDarkTheme()
) {
    val logoRes = if (isDark) {
        R.drawable.ic_komikhq_mark_dark
    } else {
        R.drawable.ic_komikhq_mark_light
    }

    Image(
        painter = painterResource(id = logoRes),
        contentDescription = "KomikHQ Logo",
        modifier = modifier.size(size)
    )
}


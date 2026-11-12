package com.komikhq.app.ui.components.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.ui.HQCircularProgressIndicator

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HQCircularProgressIndicator(
            color = HQOrange,
            size = 36.dp,
            strokeWidth = 3.dp
        )
    }
}

package com.komikhq.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InterFamily
import kotlinx.coroutines.delay

/**
 * Custom 48dp Search Field matching Figma Explore screen specification.
 * Zero dependency on Material 3.
 */
@Composable
fun HQSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = stringResource(R.string.explore_search_placeholder),
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    debounceMs: Long = 300L
) {
    var localQuery by remember(query) { mutableStateOf(query) }

    LaunchedEffect(localQuery) {
        if (localQuery != query) {
            delay(debounceMs)
            onQueryChange(localQuery)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(HQTheme.colors.surface)
            .border(1.dp, HQTheme.colors.border, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HQIcon(
                imageVector = HQIcons.Search,
                contentDescription = stringResource(R.string.action_search),
                tint = HQTheme.colors.secondaryText,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (localQuery.isEmpty()) {
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
                    value = localQuery,
                    onValueChange = { localQuery = it },
                    textStyle = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        color = HQTheme.colors.text
                    ),
                    cursorBrush = SolidColor(HQTheme.colors.primary),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch(localQuery) }),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (localQuery.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            localQuery = ""
                            onQueryChange("")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    HQIcon(
                        imageVector = HQIcons.Close,
                        contentDescription = stringResource(R.string.action_clear),
                        tint = HQTheme.colors.secondaryText,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

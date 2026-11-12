package com.komikhq.app.feature.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQFilterChip

/**
 * KHQ 19 · Catalog Filters Modal
 * Bottom-sheet-style dialog with genre, status, type, demographic, sort filters
 */
@Composable
fun CatalogFilterDialog(
    genres: List<GenreItem>,
    onApply: (genre: String?, sort: String, status: String?, type: String?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf("popular") }
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }

    val sortOptions = listOf(
        "popular" to R.string.explore_sort_popular,
        "latest" to R.string.explore_sort_latest,
        "rating" to R.string.explore_sort_rating,
        "az" to R.string.explore_sort_az
    )

    val statusOptions = listOf("ongoing", "completed")
    val typeOptions = listOf("manga", "manhwa", "manhua")

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(HQTheme.colors.background)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BasicText(
                text = stringResource(R.string.explore_filter_title),
                style = TextStyle(
                    fontFamily = InstrumentSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HQTheme.colors.text
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sort
            FilterSection(title = stringResource(R.string.explore_filter_status)) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sortOptions) { (key, labelRes) ->
                        HQFilterChip(
                            label = stringResource(labelRes),
                            selected = selectedSort == key,
                            onClick = { selectedSort = key }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status
            FilterSection(title = stringResource(R.string.explore_filter_status)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    statusOptions.forEach { status ->
                        HQFilterChip(
                            label = status.replaceFirstChar { it.uppercase() },
                            selected = selectedStatus == status,
                            onClick = {
                                selectedStatus = if (selectedStatus == status) null else status
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Type
            FilterSection(title = stringResource(R.string.explore_filter_type)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    typeOptions.forEach { type ->
                        HQFilterChip(
                            label = type.replaceFirstChar { it.uppercase() },
                            selected = selectedType == type,
                            onClick = {
                                selectedType = if (selectedType == type) null else type
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                HQButton(
                    text = stringResource(R.string.action_reset_filter),
                    onClick = {
                        selectedGenre = null
                        selectedSort = "popular"
                        selectedStatus = null
                        selectedType = null
                    },
                    variant = HQButtonVariant.OUTLINE,
                    size = HQButtonSize.MD,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                HQButton(
                    text = stringResource(R.string.action_apply_filter),
                    onClick = { onApply(selectedGenre, selectedSort, selectedStatus, selectedType) },
                    size = HQButtonSize.MD,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    content: @Composable () -> Unit
) {
    BasicText(
        text = title,
        style = TextStyle(
            fontFamily = InterFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = HQTheme.colors.text
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    content()
}


package com.komikhq.app.ui.components.core

@androidx.compose.runtime.Composable
fun HQSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = androidx.compose.ui.res.stringResource(com.komikhq.app.R.string.explore_search_placeholder),
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    onSearch: (String) -> Unit = {},
    debounceMs: Long = 300L
) {
    com.komikhq.app.core.ui.HQSearchField(
        query = query,
        onQueryChange = onQueryChange,
        placeholder = placeholder,
        modifier = modifier,
        onSearch = onSearch,
        debounceMs = debounceMs
    )
}

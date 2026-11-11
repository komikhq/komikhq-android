package com.komikhq.app.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.ComicsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DiscoverUiState {
    data object Loading : DiscoverUiState
    data class Success(
        val trending: List<ComicItem>,
        val latest: List<ComicItem>
    ) : DiscoverUiState
    data class Error(val message: String) : DiscoverUiState
}

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val comicsApi: ComicsApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

    init {
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _uiState.value = DiscoverUiState.Loading
            try {
                // Fetch trending comics
                val trendingRes = comicsApi.listTrendingComics(period = "all")
                val trendingList = (trendingRes.comics ?: emptyList()).mapNotNull { item ->
                    (item as? Map<*, *>)?.let { ComicItem.fromMap(it) }
                }

                // Fetch latest comics
                val latestRes = comicsApi.listComics(limit = "20")
                val latestList = (latestRes.comics ?: emptyList()).mapNotNull { item ->
                    (item as? Map<*, *>)?.let { ComicItem.fromMap(it) }
                }

                _uiState.value = DiscoverUiState.Success(
                    trending = if (trendingList.isNotEmpty()) trendingList else latestList.take(5),
                    latest = latestList
                )
            } catch (e: Exception) {
                _uiState.value = DiscoverUiState.Error(e.message ?: "Failed to load comics feed")
            }
        }
    }
}

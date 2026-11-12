package com.komikhq.app.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.BookmarksApi
import com.komikhq.sdk.apis.ReadingHistoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LibraryTab { BOOKMARKS, HISTORY, DOWNLOADS }

data class LibraryData(
    val bookmarks: List<ComicItem>,
    val history: List<ComicItem>,
    val downloads: List<ComicItem>
)

sealed interface LibraryUiState {
    data object Loading : LibraryUiState
    data class Success(val data: LibraryData) : LibraryUiState
    data class Error(val message: String) : LibraryUiState
}

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val bookmarksApi: BookmarksApi,
    private val readingHistoryApi: ReadingHistoryApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<LibraryUiState>(LibraryUiState.Loading)
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow(LibraryTab.BOOKMARKS)
    val selectedTab: StateFlow<LibraryTab> = _selectedTab.asStateFlow()

    init {
        loadLibrary()
    }

    fun loadLibrary() {
        viewModelScope.launch {
            _uiState.value = LibraryUiState.Loading
            try {
                val bookmarks = fetchBookmarks()
                val history = fetchHistory()

                _uiState.value = LibraryUiState.Success(
                    LibraryData(
                        bookmarks = bookmarks,
                        history = history,
                        downloads = emptyList() // local-only, managed separately
                    )
                )
            } catch (e: Exception) {
                _uiState.value = LibraryUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }

    fun onTabSelected(tab: LibraryTab) {
        _selectedTab.value = tab
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun fetchBookmarks(): List<ComicItem> {
        return try {
            val response = bookmarksApi.listBookmarks()
            val list = response.bookmarks as? List<*> ?: return emptyList()
            list.mapNotNull { item ->
                val map = item as? Map<*, *> ?: return@mapNotNull null
                val comicMap = map["comic"] as? Map<*, *> ?: return@mapNotNull null
                ComicItem.fromMap(comicMap)
            }
        } catch (_: Exception) { emptyList() }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun fetchHistory(): List<ComicItem> {
        return try {
            val response = readingHistoryApi.listReadingHistory()
            val list = response.history as? List<*> ?: return emptyList()
            list.mapNotNull { item ->
                val map = item as? Map<*, *> ?: return@mapNotNull null
                val comicMap = map["comic"] as? Map<*, *> ?: return@mapNotNull null
                ComicItem.fromMap(comicMap)
            }
        } catch (_: Exception) { emptyList() }
    }
}


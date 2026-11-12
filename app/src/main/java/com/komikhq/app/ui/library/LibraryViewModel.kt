package com.komikhq.app.ui.library

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

enum class LibraryTab {
    BOOKMARKS,
    HISTORY,
    DOWNLOADS
}

data class LibraryUiState(
    val selectedTab: LibraryTab = LibraryTab.BOOKMARKS,
    val isLoading: Boolean = false,
    val bookmarks: List<ComicItem> = emptyList(),
    val history: List<ComicItem> = emptyList(),
    val downloads: List<ComicItem> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val bookmarksApi: BookmarksApi,
    private val readingHistoryApi: ReadingHistoryApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState(isLoading = true))
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        loadDataForCurrentTab()
    }

    fun onTabSelected(tab: LibraryTab) {
        if (_uiState.value.selectedTab == tab) return
        _uiState.value = _uiState.value.copy(selectedTab = tab)
        loadDataForCurrentTab()
    }

    fun loadDataForCurrentTab() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                when (_uiState.value.selectedTab) {
                    LibraryTab.BOOKMARKS -> {
                        val response = bookmarksApi.listBookmarks()
                        val list = parseComicList(response.bookmarks)
                        _uiState.value = _uiState.value.copy(isLoading = false, bookmarks = list)
                    }
                    LibraryTab.HISTORY -> {
                        val response = readingHistoryApi.listReadingHistory()
                        val list = parseComicList(response.history)
                        _uiState.value = _uiState.value.copy(isLoading = false, history = list)
                    }
                    LibraryTab.DOWNLOADS -> {
                        // Downloads from local offline database / WorkManager
                        _uiState.value = _uiState.value.copy(isLoading = false, downloads = emptyList())
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Gagal memuat koleksi"
                )
            }
        }
    }

    private fun parseComicList(rawList: List<*>?): List<ComicItem> {
        if (rawList == null) return emptyList()
        return rawList.mapNotNull { item ->
            val map = item as? Map<*, *> ?: return@mapNotNull null
            val comicMap = map["comic"] as? Map<*, *> ?: map
            ComicItem.fromMap(comicMap)
        }
    }
}

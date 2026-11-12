package com.komikhq.app.feature.reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.sdk.apis.ChaptersApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ReadingMode { VERTICAL, PAGED }

data class ReaderData(
    val comicTitle: String,
    val chapterTitle: String,
    val chapterNumber: String,
    val pages: List<String>,
    val hasPrevChapter: Boolean,
    val hasNextChapter: Boolean,
    val prevChapterNumber: String?,
    val nextChapterNumber: String?
)

sealed interface ReaderUiState {
    data object Loading : ReaderUiState
    data class Success(val data: ReaderData) : ReaderUiState
    data class Error(val message: String) : ReaderUiState
}

@HiltViewModel
class ReaderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chaptersApi: ChaptersApi
) : ViewModel() {

    private val comicSlug: String = savedStateHandle["slug"] ?: ""
    private val chapterNumber: String = savedStateHandle["chapterNumber"] ?: ""

    private val _uiState = MutableStateFlow<ReaderUiState>(ReaderUiState.Loading)
    val uiState: StateFlow<ReaderUiState> = _uiState.asStateFlow()

    private val _readingMode = MutableStateFlow(ReadingMode.VERTICAL)
    val readingMode: StateFlow<ReadingMode> = _readingMode.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _controlsVisible = MutableStateFlow(false)
    val controlsVisible: StateFlow<Boolean> = _controlsVisible.asStateFlow()

    init {
        loadChapter(chapterNumber)
    }

    fun loadChapter(number: String) {
        viewModelScope.launch {
            _uiState.value = ReaderUiState.Loading
            try {
                val response = chaptersApi.getPublicChapter(
                    slug = comicSlug,
                    chapterSlug = number
                )
                val rootMap = response as? Map<*, *> ?: emptyMap<String, Any>()
                val chapterMap = rootMap["chapter"] as? Map<*, *> ?: rootMap
                val pagesRaw = chapterMap["pages"] as? List<*>
                val pages = pagesRaw?.mapNotNull { page ->
                    when (page) {
                        is String -> page
                        is Map<*, *> -> page["url"]?.toString()
                        else -> null
                    }
                } ?: emptyList()

                val navigation = chapterMap["navigation"] as? Map<*, *>
                val prevNum = navigation?.get("prevChapter")?.toString()
                val nextNum = navigation?.get("nextChapter")?.toString()

                _uiState.value = ReaderUiState.Success(
                    ReaderData(
                        comicTitle = chapterMap["comicTitle"]?.toString() ?: comicSlug,
                        chapterTitle = chapterMap["title"]?.toString() ?: "",
                        chapterNumber = number,
                        pages = pages,
                        hasPrevChapter = prevNum != null,
                        hasNextChapter = nextNum != null,
                        prevChapterNumber = prevNum,
                        nextChapterNumber = nextNum
                    )
                )
                _currentPage.value = 0
            } catch (e: Exception) {
                _uiState.value = ReaderUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }

    fun toggleControls() {
        _controlsVisible.value = !_controlsVisible.value
    }

    fun setReadingMode(mode: ReadingMode) {
        _readingMode.value = mode
    }

    fun onPageChanged(page: Int) {
        _currentPage.value = page
    }

    fun navigateToNextChapter() {
        val data = (_uiState.value as? ReaderUiState.Success)?.data ?: return
        data.nextChapterNumber?.let { loadChapter(it) }
    }

    fun navigateToPrevChapter() {
        val data = (_uiState.value as? ReaderUiState.Success)?.data ?: return
        data.prevChapterNumber?.let { loadChapter(it) }
    }
}


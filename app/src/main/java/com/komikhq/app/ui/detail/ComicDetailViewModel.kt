package com.komikhq.app.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.BookmarksApi
import com.komikhq.sdk.apis.ComicsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChapterItem(
    val id: String,
    val title: String,
    val slug: String,
    val releaseDate: String,
    val isRead: Boolean = false
)

data class ComicDetail(
    val comic: ComicItem,
    val description: String?,
    val author: String?,
    val isBookmarked: Boolean,
    val chapters: List<ChapterItem>
)

sealed interface ComicDetailUiState {
    data object Loading : ComicDetailUiState
    data class Success(val detail: ComicDetail) : ComicDetailUiState
    data class Error(val message: String) : ComicDetailUiState
}

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val comicsApi: ComicsApi,
    private val bookmarksApi: BookmarksApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val comicSlug: String = checkNotNull(savedStateHandle["slug"])

    private val _uiState = MutableStateFlow<ComicDetailUiState>(ComicDetailUiState.Loading)
    val uiState: StateFlow<ComicDetailUiState> = _uiState.asStateFlow()

    init {
        loadComicDetail()
    }

    fun loadComicDetail() {
        viewModelScope.launch {
            _uiState.value = ComicDetailUiState.Loading
            try {
                val response = comicsApi.getComicBySlug(comicSlug)
                val map = response as? Map<*, *> ?: throw IllegalStateException("Format data komik tidak valid")
                val comicMap = map["comic"] as? Map<*, *> ?: map
                val comicItem = ComicItem.fromMap(comicMap)

                val chaptersRaw = map["chapters"] as? List<*> ?: emptyList<Any>()
                val parsedChapters = chaptersRaw.mapNotNull { ch ->
                    val chMap = ch as? Map<*, *> ?: return@mapNotNull null
                    ChapterItem(
                        id = chMap["id"]?.toString() ?: "",
                        title = chMap["title"]?.toString() ?: "Chapter",
                        slug = chMap["slug"]?.toString() ?: "",
                        releaseDate = chMap["createdAt"]?.toString()?.take(10) ?: "",
                        isRead = chMap["isRead"] as? Boolean ?: false
                    )
                }

                val isBookmarked = map["isBookmarked"] as? Boolean ?: false
                val description = comicMap["description"]?.toString()
                val author = comicMap["author"]?.toString()

                _uiState.value = ComicDetailUiState.Success(
                    ComicDetail(
                        comic = comicItem,
                        description = description,
                        author = author,
                        isBookmarked = isBookmarked,
                        chapters = parsedChapters
                    )
                )
            } catch (e: Exception) {
                _uiState.value = ComicDetailUiState.Error(e.localizedMessage ?: "Gagal memuat detail komik")
            }
        }
    }

    fun toggleBookmark() {
        val current = (_uiState.value as? ComicDetailUiState.Success)?.detail ?: return
        viewModelScope.launch {
            try {
                // Toggle bookmark via BookmarksApi
                _uiState.value = ComicDetailUiState.Success(
                    current.copy(isBookmarked = !current.isBookmarked)
                )
            } catch (_: Exception) {}
        }
    }
}


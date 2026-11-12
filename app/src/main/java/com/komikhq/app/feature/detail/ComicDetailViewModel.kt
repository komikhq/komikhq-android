package com.komikhq.app.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.sdk.apis.BookmarksApi
import com.komikhq.sdk.apis.ChaptersApi
import com.komikhq.sdk.apis.ComicsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ComicDetail(
    val id: String,
    val title: String,
    val slug: String,
    val coverUrl: String?,
    val synopsis: String,
    val status: String?,
    val type: String?,
    val author: String?,
    val genres: List<String>,
    val totalChapters: Int,
    val rating: Double?
)

data class ChapterItem(
    val id: String,
    val title: String,
    val number: String,
    val slug: String,
    val createdAt: String?,
    val isRead: Boolean = false,
    val isDownloaded: Boolean = false
)

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val comic: ComicDetail,
        val chapters: List<ChapterItem>,
        val isBookmarked: Boolean
    ) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val comicsApi: ComicsApi,
    private val chaptersApi: ChaptersApi,
    private val bookmarksApi: BookmarksApi
) : ViewModel() {

    private val slug: String = savedStateHandle["slug"] ?: ""

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val comicResponse = comicsApi.getComicBySlug(slug)
                val rootMap = comicResponse as? Map<*, *> ?: emptyMap<String, Any>()
                val comicMap = rootMap["comic"] as? Map<*, *> ?: rootMap

                val comic = ComicDetail(
                    id = comicMap["id"]?.toString() ?: "",
                    title = comicMap["title"]?.toString() ?: "",
                    slug = comicMap["slug"]?.toString() ?: slug,
                    coverUrl = comicMap["coverUrl"]?.toString(),
                    synopsis = comicMap["synopsis"]?.toString() ?: "",
                    status = comicMap["status"]?.toString(),
                    type = comicMap["type"]?.toString()?.uppercase(),
                    author = comicMap["author"]?.toString(),
                    genres = (comicMap["genres"] as? List<*>)?.mapNotNull {
                        (it as? Map<*, *>)?.get("name")?.toString()
                    } ?: emptyList(),
                    totalChapters = (comicMap["totalChapters"] as? Number)?.toInt() ?: 0,
                    rating = (comicMap["rating"] as? Number)?.toDouble()
                )

                val chaptersRaw = (rootMap["chapters"] ?: comicMap["chapters"]) as? List<*>
                val chaptersList = parseChapters(chaptersRaw)

                val isBookmarked = checkBookmark(comic.id)

                _uiState.value = DetailUiState.Success(
                    comic = comic,
                    chapters = chaptersList,
                    isBookmarked = isBookmarked
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }

    fun toggleBookmark() {
        val current = (_uiState.value as? DetailUiState.Success) ?: return
        viewModelScope.launch {
            try {
                if (current.isBookmarked) {
                    bookmarksApi.removeBookmark(comicId = current.comic.id)
                } else {
                    bookmarksApi.saveBookmark(com.komikhq.sdk.models.SaveBookmarkRequest(comicId = current.comic.id))
                }
                _uiState.value = current.copy(isBookmarked = !current.isBookmarked)
            } catch (_: Exception) { }
        }
    }

    private suspend fun checkBookmark(comicId: String): Boolean {
        return try {
            val response = bookmarksApi.listBookmarks()
            val list = response.bookmarks as? List<*>
            list?.any { item ->
                val map = item as? Map<*, *>
                val comic = map?.get("comic") as? Map<*, *>
                comic?.get("id")?.toString() == comicId
            } ?: false
        } catch (_: Exception) { false }
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseChapters(rawList: List<*>?): List<ChapterItem> {
        return rawList?.mapNotNull { item ->
            val map = item as? Map<*, *> ?: return@mapNotNull null
            ChapterItem(
                id = map["id"]?.toString() ?: "",
                title = map["title"]?.toString() ?: "",
                number = map["number"]?.toString() ?: "",
                slug = map["slug"]?.toString() ?: "",
                createdAt = map["createdAt"]?.toString()
            )
        } ?: emptyList()
    }
}


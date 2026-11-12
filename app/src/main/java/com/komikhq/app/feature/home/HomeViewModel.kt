package com.komikhq.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.ComicsApi
import com.komikhq.sdk.apis.ReadingHistoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ContinueReadingItem(
    val comicTitle: String,
    val comicSlug: String,
    val coverUrl: String?,
    val chapterTitle: String,
    val chapterSlug: String,
    val currentPage: Int,
    val totalPages: Int
)

data class HomeFeed(
    val featuredComics: List<ComicItem>,
    val latestComics: List<ComicItem>,
    val popularComics: List<ComicItem>,
    val continueReading: ContinueReadingItem?
)

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val feed: HomeFeed) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comicsApi: ComicsApi,
    private val readingHistoryApi: ReadingHistoryApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeFeed()
    }

    fun loadHomeFeed() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val trendingResponse = comicsApi.listTrendingComics(period = "weekly", limit = "5")
                val trendingList = parseComicList(trendingResponse.comics)

                val latestResponse = comicsApi.listComics(limit = "10", sort = "latest")
                val latestList = parseComicList(latestResponse.comics)

                val popularResponse = comicsApi.listComics(limit = "10", sort = "popular")
                val popularList = parseComicList(popularResponse.comics)

                val continueReading = fetchContinueReading()

                _uiState.value = HomeUiState.Success(
                    feed = HomeFeed(
                        featuredComics = trendingList,
                        latestComics = latestList,
                        popularComics = popularList,
                        continueReading = continueReading
                    )
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }

    private suspend fun fetchContinueReading(): ContinueReadingItem? {
        return try {
            val response = readingHistoryApi.listReadingHistory()
            val list = response.history as? List<*>
            val first = list?.firstOrNull() as? Map<*, *> ?: return null
            val comicMap = first["comic"] as? Map<*, *>
            val chapterMap = first["chapter"] as? Map<*, *>

            ContinueReadingItem(
                comicTitle = comicMap?.get("title")?.toString() ?: "",
                comicSlug = comicMap?.get("slug")?.toString() ?: "",
                coverUrl = comicMap?.get("coverUrl")?.toString(),
                chapterTitle = chapterMap?.get("title")?.toString() ?: "",
                chapterSlug = chapterMap?.get("slug")?.toString() ?: "",
                currentPage = (first["page"] as? Number)?.toInt() ?: 1,
                totalPages = (first["totalPages"] as? Number)?.toInt() ?: 1
            )
        } catch (_: Exception) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseComicList(rawList: List<*>?): List<ComicItem> {
        if (rawList == null) return emptyList()
        return rawList.mapNotNull { item ->
            when (item) {
                is Map<*, *> -> {
                    val comicMap = item["comic"] as? Map<*, *> ?: item
                    ComicItem.fromMap(comicMap)
                }
                else -> null
            }
        }
    }
}


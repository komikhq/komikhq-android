package com.komikhq.app.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.ComicsApi
import com.komikhq.sdk.apis.GenresApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GenreItem(val id: String, val name: String, val slug: String)

data class ExploreData(
    val comics: List<ComicItem>,
    val genres: List<GenreItem>,
    val totalCount: Int = 0
)

sealed interface ExploreUiState {
    data object Loading : ExploreUiState
    data class Success(val data: ExploreData) : ExploreUiState
    data class Error(val message: String) : ExploreUiState
}

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val comicsApi: ComicsApi,
    private val genresApi: GenresApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre.asStateFlow()

    private val _selectedSort = MutableStateFlow("popular")
    val selectedSort: StateFlow<String> = _selectedSort.asStateFlow()

    init {
        loadExplore()
    }

    fun loadExplore() {
        viewModelScope.launch {
            _uiState.value = ExploreUiState.Loading
            try {
                val genresResponse = genresApi.listGenres()
                val genresList = parseGenres(genresResponse.genres)

                val comicsResponse = comicsApi.listComics(
                    limit = "20",
                    sort = _selectedSort.value,
                    genre = _selectedGenre.value,
                    search = _searchQuery.value.takeIf { it.isNotBlank() }
                )
                val comicsList = parseComicList(comicsResponse.comics)

                _uiState.value = ExploreUiState.Success(
                    ExploreData(
                        comics = comicsList,
                        genres = genresList,
                        totalCount = comicsList.size
                    )
                )
            } catch (e: Exception) {
                _uiState.value = ExploreUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearch() {
        loadExplore()
    }

    fun onGenreSelected(genreSlug: String?) {
        _selectedGenre.value = genreSlug
        loadExplore()
    }

    fun onSortChanged(sort: String) {
        _selectedSort.value = sort
        loadExplore()
    }

    /** Alias used by Catalog sub-screens (KHQ 60-62). */
    fun setSortBy(sort: String) {
        onSortChanged(sort)
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseGenres(rawList: List<*>?): List<GenreItem> {
        return rawList?.mapNotNull { item ->
            val map = item as? Map<*, *> ?: return@mapNotNull null
            GenreItem(
                id = map["id"]?.toString() ?: "",
                name = map["name"]?.toString() ?: "",
                slug = map["slug"]?.toString() ?: ""
            )
        } ?: emptyList()
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


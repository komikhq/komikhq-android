package com.komikhq.app.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.model.ComicItem
import com.komikhq.sdk.apis.ComicsApi
import com.komikhq.sdk.apis.GenresApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GenreItem(
    val id: String,
    val name: String,
    val slug: String
)

data class ExploreUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedGenreSlug: String? = null,
    val genres: List<GenreItem> = emptyList(),
    val comics: List<ComicItem> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val comicsApi: ComicsApi,
    private val genresApi: GenresApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState(isLoading = true))
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Fetch genres dynamically from API
                val genresResponse = genresApi.listGenres()
                val parsedGenres = parseGenres(genresResponse.genres)

                // Fetch catalog comics from API
                val comicsResponse = comicsApi.browseComics(limit = "20")
                val parsedComics = parseComics(comicsResponse.comics)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    genres = parsedGenres,
                    comics = parsedComics
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Gagal memuat katalog"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400) // debounce search
            fetchComicsWithFilter(query = query, genre = _uiState.value.selectedGenreSlug)
        }
    }

    fun onGenreSelected(genreSlug: String?) {
        if (_uiState.value.selectedGenreSlug == genreSlug) return
        _uiState.value = _uiState.value.copy(selectedGenreSlug = genreSlug)
        fetchComicsWithFilter(query = _uiState.value.searchQuery, genre = genreSlug)
    }

    private fun fetchComicsWithFilter(query: String, genre: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val response = comicsApi.browseComics(
                    q = query.ifBlank { null },
                    genre = genre,
                    limit = "30"
                )
                val parsedComics = parseComics(response.comics)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    comics = parsedComics
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Gagal mencari komik"
                )
            }
        }
    }

    private fun parseGenres(rawList: List<*>?): List<GenreItem> {
        if (rawList == null) return emptyList()
        return rawList.mapNotNull { item ->
            val map = item as? Map<*, *> ?: return@mapNotNull null
            GenreItem(
                id = map["id"]?.toString() ?: "",
                name = map["name"]?.toString() ?: "",
                slug = map["slug"]?.toString() ?: ""
            )
        }
    }

    private fun parseComics(rawList: List<*>?): List<ComicItem> {
        if (rawList == null) return emptyList()
        return rawList.mapNotNull { item ->
            val map = item as? Map<*, *> ?: return@mapNotNull null
            val comicMap = map["comic"] as? Map<*, *> ?: map
            ComicItem.fromMap(comicMap)
        }
    }
}


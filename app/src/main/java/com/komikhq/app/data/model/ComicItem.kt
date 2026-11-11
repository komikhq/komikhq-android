package com.komikhq.app.data.model

data class ComicItem(
    val id: String,
    val title: String,
    val slug: String,
    val coverUrl: String?,
    val type: String?,
    val status: String?,
    val totalChapters: Int,
    val genres: List<String>
) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun fromMap(map: Map<*, *>): ComicItem {
            val id = map["id"]?.toString() ?: ""
            val title = map["title"]?.toString() ?: "Untitled"
            val slug = map["slug"]?.toString() ?: ""
            val coverUrl = map["coverUrl"]?.toString()
            val type = map["type"]?.toString()?.uppercase()
            val status = map["status"]?.toString()?.replaceFirstChar { it.uppercase() }
            val totalChapters = when (val c = map["totalChapters"]) {
                is Number -> c.toInt()
                is String -> c.toIntOrNull() ?: 0
                else -> 0
            }
            val genresList = (map["genres"] as? List<*>)?.mapNotNull { g ->
                (g as? Map<*, *>)?.get("name")?.toString()
            } ?: emptyList()

            return ComicItem(
                id = id,
                title = title,
                slug = slug,
                coverUrl = coverUrl,
                type = type,
                status = status,
                totalChapters = totalChapters,
                genres = genresList
            )
        }
    }
}


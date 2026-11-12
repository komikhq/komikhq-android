package com.komikhq.app.ui.navigation

object Routes {
    const val AUTH = "auth"
    const val MAIN = "main"
    const val DETAIL = "comic/{slug}"
    const val READER = "comic/{slug}/chapter/{chapterSlug}"

    fun detail(slug: String) = "comic/$slug"
    fun reader(comicSlug: String, chapterSlug: String) = "comic/$comicSlug/chapter/$chapterSlug"
}

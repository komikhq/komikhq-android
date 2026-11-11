package com.komikhq.app

import com.komikhq.app.data.network.AnySerializer
import com.komikhq.sdk.infrastructure.Serializer
import com.komikhq.sdk.models.ListComics200Response
import com.komikhq.sdk.models.ListTrendingComics200Response
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class AnySerializerTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        serializersModule = SerializersModule {
            include(Serializer.kotlinxSerializationAdapters)
            contextual(AnySerializer)
        }
    }

    @Test
    fun testListTrendingComicsDeserialization() {
        val sampleJson = """
        {
            "period": "all",
            "total": 100,
            "comics": [
                {
                    "id": "comic-1",
                    "slug": "solo-leveling",
                    "title": "Solo Leveling",
                    "cover": "https://example.com/cover.jpg",
                    "rating": 9.8,
                    "type": "manhwa",
                    "status": "ongoing",
                    "latestChapter": "Chapter 200"
                }
            ]
        }
        """.trimIndent()

        val response = json.decodeFromString<ListTrendingComics200Response>(sampleJson)
        assertEquals("all", response.period)
        assertNotNull(response.comics)
        assertEquals(1, response.comics.size)

        val firstComic = response.comics[0] as? Map<*, *>
        assertNotNull(firstComic)
        assertEquals("solo-leveling", firstComic?.get("slug"))
        assertEquals("Solo Leveling", firstComic?.get("title"))
    }

    @Test
    fun testListComicsDeserialization() {
        val sampleJson = """
        {
            "comics": [
                {
                    "id": "comic-2",
                    "slug": "tower-of-god",
                    "title": "Tower of God",
                    "cover": "https://example.com/cover2.jpg",
                    "rating": 9.5
                }
            ],
            "pagination": {
                "page": 1,
                "limit": 20,
                "total": 500
            }
        }
        """.trimIndent()

        val response = json.decodeFromString<ListComics200Response>(sampleJson)
        assertNotNull(response.comics)
        assertEquals(1, response.comics.size)

        val firstComic = response.comics[0] as? Map<*, *>
        assertNotNull(firstComic)
        assertEquals("tower-of-god", firstComic?.get("slug"))
    }
}


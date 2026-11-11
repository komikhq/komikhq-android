package com.komikhq.app.data.network

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull

/**
 * Contextual serializer for `kotlin.Any` used by SDK response models
 * when encountering `@Contextual Any?` in OpenAPI generated schemas.
 */
object AnySerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Any")

    override fun serialize(encoder: Encoder, value: Any) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw SerializationException("AnySerializer can only be used with Json")
        jsonEncoder.encodeJsonElement(valueToJsonElement(value))
    }

    override fun deserialize(decoder: Decoder): Any {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("AnySerializer can only be used with Json")
        val element = jsonDecoder.decodeJsonElement()
        return jsonElementToAny(element) ?: ""
    }

    private fun jsonElementToAny(element: JsonElement): Any? {
        return when (element) {
            is JsonNull -> null
            is JsonPrimitive -> {
                if (element.isString) {
                    element.content
                } else {
                    element.booleanOrNull
                        ?: element.longOrNull
                        ?: element.doubleOrNull
                        ?: element.content
                }
            }
            is JsonArray -> element.map { jsonElementToAny(it) }
            is JsonObject -> element.mapValues { jsonElementToAny(it.value) }
        }
    }

    private fun valueToJsonElement(value: Any?): JsonElement {
        return when (value) {
            null -> JsonNull
            is Boolean -> JsonPrimitive(value)
            is Number -> JsonPrimitive(value)
            is String -> JsonPrimitive(value)
            is List<*> -> JsonArray(value.map { valueToJsonElement(it) })
            is Map<*, *> -> JsonObject(value.entries.associate { it.key.toString() to valueToJsonElement(it.value) })
            is JsonElement -> value
            else -> JsonPrimitive(value.toString())
        }
    }
}

